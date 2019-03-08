package com.payline.mobile.androidsdk.payment.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payline.mobile.androidsdk.R
import com.payline.mobile.androidsdk.core.domain.SdkAction
import com.payline.mobile.androidsdk.core.presentation.WebFragment
import com.payline.mobile.androidsdk.core.presentation.WebViewModel
import com.payline.mobile.androidsdk.core.util.IntentExtraDelegate
import com.payline.mobile.androidsdk.payment.domain.PaymentSdkAction
import kotlinx.android.synthetic.main.activity_payment.*

internal class PaymentActivity: AppCompatActivity() {

    companion object {

        private var Intent.uri by IntentExtraDelegate.Uri("EXTRA_URI")

        fun buildIntent(context: Context, uri: Uri): Intent {
            return Intent(context, PaymentActivity::class.java).apply {
                this.uri = uri
            }
        }
    }

    private lateinit var viewModel: WebViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        viewModel = ViewModelProviders.of(this).get(WebViewModel::class.java)

        viewModel.isLoading.observe(this, Observer<Boolean> {
            progressBar.visibility = if(it) View.VISIBLE else View.GONE
        })

        viewModel.hideCancelButton.observe(this, Observer<Boolean> {
            b_cancel_payment_activity.visibility = if(it) View.GONE else View.VISIBLE
        })

        viewModel.finishUi.observe(this, Observer<Boolean> {
            if(it) { finish() }
        })

        if (savedInstanceState == null) {

            supportFragmentManager.executePendingTransactions()

            val uri = intent.uri ?: return
            val webFragment = WebFragment.createInstance(uri)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout_fragmentContainer, webFragment, WebFragment::class.java.name)
                .commit()
        }

        b_cancel_payment_activity.setOnClickListener {
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(SdkAction.BROADCAST_SDK_ACTION).apply {
                putExtra(SdkAction.EXTRA_SDK_ACTION, PaymentSdkAction.EndToken(false, null))
            })
        }
    }

    override fun onBackPressed() {
        //Disable button back pressed on this activity
    }

}
