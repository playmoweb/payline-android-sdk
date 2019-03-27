package com.payline.mobile.sampleapp

import android.content.Context
import android.content.SharedPreferences
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
import java.lang.NumberFormatException
import java.util.*

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
        paymentButton.isEnabled = false
        paymentUri = null
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

    private fun getWalletId(): String {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val walletId = sharedPreferences.getString("WalletId", UUID.randomUUID().toString())!!
        if(!sharedPreferences.contains("WalletId")) {
            sharedPreferences.edit().putString("WalletId", walletId).apply()
        }
        return walletId
    }

    private fun setupButtons() {

        paymentTokenButton.setOnClickListener { fetchTokenForPayment() }
        walletTokenButton.setOnClickListener { fetchTokenForWallet() }

        paymentButton.setOnClickListener {
            paymentUri ?: return@setOnClickListener
            paymentController.showPaymentForm(paymentUri!!)
        }

        walletButton.setOnClickListener {
            walletUri ?: return@setOnClickListener
            walletController.manageWebWallet(walletUri!!)
        }
    }

    private fun fetchTokenForPayment() {
        try {

            val amount = editText.text.toString().toDouble()
            if(amount <= 0) throw NumberFormatException()

            progressBar.show()
            TokenFetcher(fetchTokenCallback)
                .execute(FetchTokenParams.testPaymentParams(amount, getWalletId()))

        } catch(t: NumberFormatException) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchTokenForWallet() {
        progressBar.show()
        TokenFetcher(fetchTokenCallback)
            .execute(FetchTokenParams.testWalletParams(getWalletId()))
    }

    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        progressBar.hide()
        result?.redirectUrl?.let {
            when(result.type) {
                FetchTokenParams.Type.PAYMENT -> {
                    paymentUri = Uri.parse(it)
                    printFetchTokenResult(paymentUri)
                    paymentButton.isEnabled = true

                }
                FetchTokenParams.Type.WALLET -> {
                    walletUri = Uri.parse(it)
                    printFetchTokenResult(walletUri)
                    walletButton.isEnabled = true
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

