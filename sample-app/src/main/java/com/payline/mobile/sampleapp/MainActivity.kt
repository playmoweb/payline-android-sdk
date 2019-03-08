package com.payline.mobile.sampleapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.payline.mobile.androidsdk.core.data.ContextInfoKey
import com.payline.mobile.androidsdk.core.data.ContextInfoResult
import com.payline.mobile.androidsdk.core.data.WidgetState
import com.payline.mobile.androidsdk.payment.PaymentController
import com.payline.mobile.androidsdk.payment.PaymentControllerListener
import com.payline.mobile.androidsdk.wallet.WalletController
import com.payline.mobile.androidsdk.wallet.WalletControllerListener
import com.payline.mobile.tokenfetcher.FetchTokenParams
import com.payline.mobile.tokenfetcher.FetchTokenResult
import com.payline.mobile.tokenfetcher.TokenFetcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PaymentControllerListener, WalletControllerListener {

    private lateinit var paymentController: PaymentController
    private lateinit var walletController: WalletController

    private var uri: Uri? = null

    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        result?.redirectUrl?.let {
            uri = Uri.parse(it)
        }
        if(uri == null) {
            Toast.makeText(this@MainActivity, "Couldn't get new uri", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@MainActivity, "Got new uri: $uri", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paymentController = PaymentController()
        paymentController.registerListener(this, this)
        walletController = WalletController()
        walletController.registerListener(this, this)

        paymentTokenButton.setOnClickListener { fetchTokenForPayment() }
        walletTokenButton.setOnClickListener { fetchTokenForWallet() }

        paymentButton.setOnClickListener {
            uri ?: return@setOnClickListener
            paymentController.showPaymentForm(uri!!)
        }

        walletButton.setOnClickListener {
            uri ?: return@setOnClickListener
            walletController.manageWebWallet(uri!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        paymentController.unregisterListener()
        walletController.unregisterListener()
    }

    private fun fetchTokenForPayment() {
        TokenFetcher(fetchTokenCallback)
            .execute(FetchTokenParams.testPaymentParams())
    }

    private fun fetchTokenForWallet() {
        TokenFetcher(fetchTokenCallback)
            .execute(FetchTokenParams.testWalletParams())
    }

    override fun didShowPaymentForm() {
        paymentController.getLanguage()
        paymentController.getIsSandbox()
        paymentController.getContextInfo(ContextInfoKey.CURRENCY_CODE)
        paymentController.getContextInfo(ContextInfoKey.AMOUNT_SMALLEST_UNIT)
        paymentController.getContextInfo(ContextInfoKey.CURRENCY_DIGITS)
        paymentController.getContextInfo(ContextInfoKey.ORDER_DETAILS)

//        paymentController.endToken(false, null)
    }

    override fun didFinishPaymentForm(state: WidgetState) {
        Toast.makeText(this, "didFinishPayementForm called with $state", Toast.LENGTH_LONG).show()
    }

    override fun didGetIsSandbox(isSandbox: Boolean) {
        Toast.makeText(this, "got isSandbox: $isSandbox", Toast.LENGTH_SHORT).show()
    }

    override fun didGetLanguage(language: String) {
        Toast.makeText(this, "got language: $language", Toast.LENGTH_SHORT).show()
    }

    override fun didGetContextInfo(info: ContextInfoResult) {
        Toast.makeText(this, "got ContextInfo: $info", Toast.LENGTH_SHORT).show()
    }

    override fun didShowManageWebWallet() {
        Toast.makeText(this, "didShowManageWebWallet called", Toast.LENGTH_LONG).show()
    }
}

