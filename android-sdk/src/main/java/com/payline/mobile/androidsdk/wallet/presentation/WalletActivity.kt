package com.payline.mobile.androidsdk.wallet.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.payline.mobile.androidsdk.R
import com.payline.mobile.androidsdk.core.presentation.WebFragment
import com.payline.mobile.androidsdk.core.util.IntentExtraDelegate
import kotlinx.android.synthetic.main.activity_wallet.*

internal class WalletActivity: AppCompatActivity(), WalletInterface {

    companion object {

        private var Intent.uri by IntentExtraDelegate.Uri("EXTRA_URI")

        fun buildIntent(context: Context, uri: Uri): Intent {
            return Intent(context, WalletActivity::class.java).apply {
                this.uri = uri
            }
        }
    }

//    private val viewModel: WebViewModel by lazy {
//        ViewModelProviders.of(this).get(WebViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)

        progressBar_wallet.visibility = View.VISIBLE
        showWebFragment()
    }

    private fun showWebFragment() {

        val fragmentManager = supportFragmentManager
        fragmentManager.executePendingTransactions()

        val uri = intent.uri ?: return
        val webFragment = WebFragment.createInstance(uri)
        fragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout_fragmentContainer_wallet, webFragment, WebFragment::class.java.name)
            .commit()

        b_cancel_wallet_activity.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        //Disable button back pressed on this activity
    }

    override fun stopWalletLoader() {
        progressBar_wallet.visibility = View.GONE
    }

}

interface WalletInterface {
    fun stopWalletLoader()
}
