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

    //region Properties

    private lateinit var paymentController: PaymentController
    private lateinit var walletController: WalletController

    private var paymentUri: Uri? = null
    private var walletUri: Uri? = null

    //endregion Properties

    //region System

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paymentController = PaymentController()
        paymentController.registerListener(this, this)
        walletController = WalletController()
        walletController.registerListener(this, this)

        setupButtons()
    }

    override fun onDestroy() {
        super.onDestroy()
        paymentController.unregisterListener()
        walletController.unregisterListener()
    }

    //endregion System

    //region implements PaymentControllerListener

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

    //endregion implements PaymentControllerListener

    //region implements WalletControllerListener

    override fun didShowManageWebWallet() {
        Toast.makeText(this, "didShowManageWebWallet called", Toast.LENGTH_LONG).show()
    }

    //endregion implements WalletControllerListener

    //region private token fetching

    private fun setupButtons() {

        paymentTokenButton.setOnClickListener { fetchTokenForPayment() }
        walletTokenButton.setOnClickListener { fetchTokenForWallet() }

        paymentButton.setOnClickListener {
            paymentUri ?: return@setOnClickListener
            paymentController.showPaymentForm(paymentUri!!)
        }

        walletButton.setOnClickListener {
            paymentUri ?: return@setOnClickListener
            walletController.manageWebWallet(paymentUri!!)
        }
    }

    private fun fetchTokenForPayment() {
        TokenFetcher(fetchTokenCallback)
            .execute(FetchTokenParams.testPaymentParams())
    }

    private fun fetchTokenForWallet() {
        TokenFetcher(fetchTokenCallback)
            .execute(FetchTokenParams.testWalletParams())
    }

    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        result?.redirectUrl?.let {
            when(result.type) {
                FetchTokenParams.Type.PAYMENT -> {
                    paymentUri = Uri.parse(it)
                    printFetchTokenResult(paymentUri)
                }
                FetchTokenParams.Type.WALLET -> {
                    walletUri = Uri.parse(it)
                    printFetchTokenResult(walletUri)
                }
            }
        }
    }

    private fun printFetchTokenResult(uri: Uri?) {
        if(uri == null) {
            Toast.makeText(this@MainActivity, "Couldn't get new uri", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@MainActivity, "Got new uri: $uri", Toast.LENGTH_SHORT).show()
        }
    }

    //endregion private token fetching
}

