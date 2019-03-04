package com.myluckyday.test.paylinesdk.payment.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.myluckyday.test.paylinesdk.R
import com.myluckyday.test.paylinesdk.core.presentation.WebFragment
import com.myluckyday.test.paylinesdk.core.presentation.WebViewModel
import com.myluckyday.test.paylinesdk.core.util.IntentExtraDelegate

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

        if (savedInstanceState == null) {

            supportFragmentManager.executePendingTransactions()

            val uri = intent.uri ?: return
            val webFragment = WebFragment.createInstance(uri)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout_fragmentContainer, webFragment, WebFragment::class.java.name)
                .commit()
        }
    }

}
