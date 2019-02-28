package com.myluckyday.test.paylinesdk.payment

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.core.app.ActivityOptionsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.myluckyday.test.paylinesdk.app.domain.SdkAction
import com.myluckyday.test.paylinesdk.app.domain.SdkResult
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkAction
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult
import com.myluckyday.test.paylinesdk.payment.presentation.PaymentActivity
import org.json.JSONObject

class PaymentController {

    private var listener: PaymentControllerListener? = null
    private var context: Context? = null

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val sdkResult = intent.getParcelableExtra<PaymentSdkResult>(SdkResult.EXTRA_SDK_RESULT)
            when(sdkResult) {
                is PaymentSdkResult.DidShowPaymentForm -> listener?.didShowPaymentForm()
                is PaymentSdkResult.DidGetLanguage -> listener?.didGetLanguage(sdkResult.language)
                is PaymentSdkResult.DidGetIsSandbox -> listener?.didGetIsSandbox(sdkResult.isSandbox)
            }
        }
    }

    /**
     * TODO: doc
     */
    fun registerListener(listener: PaymentControllerListener, context: Context) {
        this.listener = listener
        this.context = context
        bman?.registerReceiver(broadcastReceiver, IntentFilter(SdkResult.BROADCAST_SDK_RESULT))
    }

    /**
     * TODO: doc
     */
    fun unregisterListener() {
        bman?.unregisterReceiver(broadcastReceiver)
        this.listener = null
        this.context = null
    }

    /**
     * TODO: doc
     */
    fun showPaymentForm(token: String, uri: Uri) {
        val c = context ?: return
        val intent = PaymentActivity.buildIntent(c, uri)
        val opts = ActivityOptionsCompat.makeCustomAnimation(c, 0, 0).toBundle()
        c.startActivity(intent, opts)
    }

    /**
     * TODO: doc
     */
    fun cancelPaymentForm() {
        endToken(true)
    }

    /**
     * TODO: doc
     */
    fun updateWebPaymentData(data: JSONObject) {
        broadcastAction(PaymentSdkAction.UpdateWebPaymentData(data))
    }

    /**
     * TODO: doc
     */
    fun getIsSandbox() {
        broadcastAction(PaymentSdkAction.IsSandbox())
    }

    /**
     * TODO: doc
     */
    fun endToken(handledByMerchant: Boolean) {
        broadcastAction(PaymentSdkAction.EndToken(handledByMerchant))
    }

    /**
     * TODO: doc
     */
    fun getLanguage() {
        broadcastAction(PaymentSdkAction.GetLanguage())
    }

    /**
     * TODO: doc
     */
    fun getContextInfo(key: String?) {
        broadcastAction(PaymentSdkAction.GetContextInfo(key))
    }

    /**
     * TODO: doc
     */
    fun finalizeShortCut() {
        broadcastAction(PaymentSdkAction.FinalizeShortCut())
    }

    /**
     * TODO: doc
     */
    fun getBuyerShortCut() {
        broadcastAction(PaymentSdkAction.GetBuyerShortCut())
    }

    private val bman: LocalBroadcastManager?
        get() {
            val c = context ?: return null
            return LocalBroadcastManager.getInstance(c)
        }

    private fun broadcastAction(action: PaymentSdkAction) {
        bman?.sendBroadcast(Intent(SdkAction.BROADCAST_SDK_ACTION).apply {
            putExtra(SdkAction.EXTRA_SDK_ACTION, action)
        })
    }

}
