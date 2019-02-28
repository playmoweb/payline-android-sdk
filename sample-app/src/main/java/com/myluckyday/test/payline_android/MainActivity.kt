package com.myluckyday.test.payline_android

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.myluckyday.test.payline_android.data.FetchTokenParams
import com.myluckyday.test.payline_android.data.FetchTokenResult
import com.myluckyday.test.payline_android.domain.TokenFetcher
import com.myluckyday.test.paylinesdk.app.util.Either
import com.myluckyday.test.paylinesdk.payment.PaymentController
import com.myluckyday.test.paylinesdk.payment.PaymentControllerListener
import com.myluckyday.test.paylinesdk.wallet.WalletControllerListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentControllerListener, WalletControllerListener {

    private lateinit var paymentController: PaymentController

    private var token: String? = null
    private var uri: Uri? = null

    private val fetchTokenCallback: (FetchTokenResult?)->Unit = { result ->
        token = result?.token
        result?.redirectUrl?.let {
            uri = Uri.parse(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        paymentController = PaymentController()
        paymentController.registerListener(this, this)

        payButton.setOnClickListener {
            token ?: return@setOnClickListener
            uri ?: return@setOnClickListener
            paymentController.showPaymentForm(token!!, uri!!)
        }

        fetchToken()
    }

    override fun onDestroy() {
        super.onDestroy()
        paymentController.unregisterListener()
    }

    private fun fetchToken() {
        TokenFetcher(fetchTokenCallback).execute(
            FetchTokenParams(
                orderRef = "00001",
                amount = 5,
                currencyCode = "EUR"
            )
        )
    }

    override fun didShowPaymentForm() {
        paymentController.getLanguage()
        paymentController.getIsSandbox()
    }

    override fun didCancelPaymentForm() {
        // TODO:
    }

    override fun didFinishPaymentForm() {
        // TODO:
    }

    override fun didGetIsSandbox(isSandbox: Boolean) {
        Log.e("MainActivity", "got isSandbox: $isSandbox")
    }

    override fun didGetLanguage(language: String) {
        Log.e("MainActivity", "got language: $language")
    }

    override fun didGetContextInfo(info: Either<JSONObject, JSONArray>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didGetBuyerShortCut(buyer: JSONObject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didShowManageWebWallet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didFinishManageWebWallet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

