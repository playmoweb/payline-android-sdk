package com.payline.mobile

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.payline.mobile.core.data.ContextInfoKey
import com.payline.mobile.core.data.ContextInfoResult
import com.payline.mobile.payment.PaymentController
import com.payline.mobile.payment.PaymentControllerListener
import com.payline.mobile.tokenfetcher.FetchTokenParams
import com.payline.mobile.tokenfetcher.FetchTokenResult
import com.payline.mobile.tokenfetcher.TokenFetcher
import com.payline.mobile.wallet.WalletController
import com.payline.mobile.wallet.WalletControllerListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PaymentControllerListener, WalletControllerListener {

    private lateinit var paymentController: PaymentController
    private lateinit var walletController: WalletController

    private var token: String? = null
    private var uri: Uri? = null

    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        token = result?.token
        result?.redirectUrl?.let {
            uri = Uri.parse(it)
        }
        if(token == null) {
            Toast.makeText(this@MainActivity, "Couldn't get new token", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@MainActivity, "Got new token: $token", Toast.LENGTH_SHORT).show()
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
            token ?: return@setOnClickListener
            uri ?: return@setOnClickListener
            paymentController.showPaymentForm(token!!, uri!!)
        }

        walletButton.setOnClickListener {
            token ?: return@setOnClickListener
            uri ?: return@setOnClickListener
            walletController.showManageWallet(token!!, uri!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        paymentController.unregisterListener()
        walletController.unregisterListener(this)
    }

    private fun fetchTokenForPayment() {
        TokenFetcher(fetchTokenCallback).execute(FetchTokenParams.testPaymentParams())
    }

    private fun fetchTokenForWallet() {
        TokenFetcher(fetchTokenCallback).execute(FetchTokenParams.testWalletParams())
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

    override fun didCancelPaymentForm() {
        Toast.makeText(this, "ended Token", Toast.LENGTH_LONG).show()
    }

    override fun didFinishPaymentForm() {
        // TODO:
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
