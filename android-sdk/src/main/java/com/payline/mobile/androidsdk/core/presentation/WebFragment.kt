package com.payline.mobile.androidsdk.core.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payline.mobile.androidsdk.R
import com.payline.mobile.androidsdk.core.domain.SdkAction
import com.payline.mobile.androidsdk.core.domain.SdkResult
import com.payline.mobile.androidsdk.core.domain.SdkResultBroadcaster
import com.payline.mobile.androidsdk.core.domain.web.ScriptAction
import com.payline.mobile.androidsdk.core.domain.web.ScriptActionExecutor
import com.payline.mobile.androidsdk.core.domain.web.WebSdkActionDelegate
import com.payline.mobile.androidsdk.core.util.BundleDelegate
import kotlinx.android.synthetic.main.fragment_web.*

internal class WebFragment: Fragment(), ScriptActionExecutor,
    SdkResultBroadcaster {

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

    private val actionDelegate: WebSdkActionDelegate by lazy {
        WebSdkActionDelegate(this, this)
    }

    private val actionReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.getParcelableExtra<SdkAction?>(SdkAction.EXTRA_SDK_ACTION) ?: return
            actionDelegate.handleAction(action)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(WebViewModel::class.java)

        // setup webView and inject script handler
        web_view.settings.javaScriptEnabled = true
        web_view.addJavascriptInterface(viewModel.scriptHandler, viewModel.scriptHandler.toString())

        // Listen for SdkAction broadcasts
        val actionFilter = IntentFilter(SdkAction.BROADCAST_SDK_ACTION)
        LocalBroadcastManager.getInstance(activity!!).registerReceiver(actionReceiver, actionFilter)

        arguments?.uri?.let {
            web_view.loadUrl(it.toString())
        }

        web_view.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                viewModel.isLoading.postValue(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(actionReceiver)
    }

    override fun executeScriptAction(action: ScriptAction, callback: (String)->Unit) {
        viewModel.scriptHandler.execute(action, web_view, callback)
    }

    override fun broadcast(result: SdkResult) {
        LocalBroadcastManager.getInstance(activity!!).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, result)
            }
        )
    }
}
