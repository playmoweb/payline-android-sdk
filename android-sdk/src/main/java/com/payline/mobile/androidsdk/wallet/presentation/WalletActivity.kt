package com.payline.mobile.androidsdk.wallet.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.payline.mobile.androidsdk.R
import com.payline.mobile.androidsdk.core.presentation.WebFragment
import com.payline.mobile.androidsdk.core.presentation.WebViewModel
import com.payline.mobile.androidsdk.core.util.IntentExtraDelegate
import kotlinx.android.synthetic.main.activity_wallet.*

internal class WalletActivity: AppCompatActivity() {

    companion object {

        private var Intent.uri by IntentExtraDelegate.Uri("EXTRA_URI")

        fun buildIntent(context: Context, uri: Uri): Intent {
            return Intent(context, WalletActivity::class.java).apply {
                this.uri = uri
            }
        }
    }

    private val viewModel: WebViewModel by lazy {
        ViewModelProviders.of(this).get(WebViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        viewModel.isLoading.observe(this, Observer {
            progressBar.visibility = if(it) View.VISIBLE else View.GONE
        })

        if(savedInstanceState == null) {

            supportFragmentManager.executePendingTransactions()

            val uri = intent.uri ?: return
            val webFragment = WebFragment.createInstance(uri)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout_fragmentContainer_wallet, webFragment, WebFragment::class.java.name)
                .commit()
        }

        b_cancel_wallet_activity.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        //Disable button back pressed on this activity
    }

}
