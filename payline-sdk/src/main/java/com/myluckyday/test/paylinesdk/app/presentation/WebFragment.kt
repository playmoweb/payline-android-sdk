package com.myluckyday.test.paylinesdk.app.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.myluckyday.test.paylinesdk.R
import com.myluckyday.test.paylinesdk.app.domain.SdkAction
import com.myluckyday.test.paylinesdk.app.domain.SdkResult
import com.myluckyday.test.paylinesdk.app.util.BundleDelegate
import com.myluckyday.test.paylinesdk.payment.domain.PaymentScriptAction
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkAction
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult
import kotlinx.android.synthetic.main.fragment_web.*

internal class WebFragment: Fragment() {

    companion object {

        private var Bundle.uri by BundleDelegate.Uri("EXTRA_URI")

        fun createInstance(uri: Uri): WebFragment {
            return WebFragment().apply {
                arguments = Bundle().apply {
                    this.uri = uri
                }
            }
        }
    }

    private lateinit var viewModel: WebViewModel

    private val actionReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.getParcelableExtra<SdkAction?>(SdkAction.EXTRA_SDK_ACTION) ?: return
            handleSdkAction(action)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(WebViewModel::class.java)

        setupWebView()
        injectScriptHandler()

        // Listen for SdkAction broadcasts
        val actionFilter = IntentFilter(SdkAction.BROADCAST_SDK_ACTION)
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(actionReceiver, actionFilter)

        arguments?.uri?.let {
            web_view.loadUrl(it.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(actionReceiver)
    }

    private fun setupWebView() {
        web_view.settings.javaScriptEnabled = true
    }

    private fun injectScriptHandler() {
        web_view.addJavascriptInterface(viewModel.scriptHandler, viewModel.scriptHandler.toString())
    }

    /**
     * TODO: need to externalize this
     * Need to map SdkAction to ScriptAction and then produce correct SdkResult (based on SdkAction)
     */
    private fun handleSdkAction(action: SdkAction) {
        when(action) {
            is PaymentSdkAction -> {
                when(action) {
                    is PaymentSdkAction.GetLanguage -> viewModel.scriptHandler.execute(PaymentScriptAction.GetLanguage, web_view) { language ->
                        broadcast(PaymentSdkResult.DidGetLanguage(language))
                    }
                    is PaymentSdkAction.IsSandbox -> viewModel.scriptHandler.execute(PaymentScriptAction.IsSandbox, web_view) { result ->
                        broadcast(PaymentSdkResult.DidGetIsSandbox(result.toBoolean()))
                    }
                }
            }
        }
    }

    private fun broadcast(result: SdkResult) {
        LocalBroadcastManager.getInstance(activity!!).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, result)
            }
        )
    }
}