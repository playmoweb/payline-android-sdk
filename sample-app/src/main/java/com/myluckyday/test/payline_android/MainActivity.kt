package com.myluckyday.test.payline_android

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.myluckyday.test.payline_android.data.FetchTokenParams
import com.myluckyday.test.payline_android.data.FetchTokenResult
import com.myluckyday.test.payline_android.domain.TokenFetcher
import com.myluckyday.test.paylinesdk.app.data.ContextInfoKey
import com.myluckyday.test.paylinesdk.app.data.ContextInfoResult
import com.myluckyday.test.paylinesdk.payment.PaymentController
import com.myluckyday.test.paylinesdk.payment.PaymentControllerListener
import com.myluckyday.test.paylinesdk.wallet.WalletController
import com.myluckyday.test.paylinesdk.wallet.WalletControllerListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*

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

        tokenButton.setOnClickListener {
            fetchToken()
        }

        payButton.setOnClickListener {
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

    private fun fetchToken() {
        TokenFetcher(fetchTokenCallback).execute(
            FetchTokenParams(
                orderRef = UUID.randomUUID().toString(),
                amount = 5,
                currencyCode = "EUR"
            )
        )
    }

    override fun didShowPaymentForm() {
        paymentController.getLanguage()
        paymentController.getIsSandbox()
        paymentController.getContextInfo(ContextInfoKey.CURRENCY_CODE)
        paymentController.getContextInfo(ContextInfoKey.AMOUNT_SMALLEST_UNIT)
        paymentController.getContextInfo(ContextInfoKey.CURRENCY_DIGITS)
        paymentController.getContextInfo(ContextInfoKey.ORDER_DETAILS)
    }

    override fun didCancelPaymentForm() {
        // TODO:
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

    override fun didGetContextInfo(info: String) {
        Toast.makeText(this, "got ContextInfo: $info", Toast.LENGTH_SHORT).show()
    }

    override fun didShowManageWebWallet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun didFinishManageWebWallet() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

