package com.payline.mobile.androidsdk.wallet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payline.mobile.androidsdk.core.domain.SdkResult
import com.payline.mobile.androidsdk.payment.PaymentControllerListener
import com.payline.mobile.androidsdk.wallet.domain.WalletSdkResult
import com.payline.mobile.androidsdk.wallet.presentation.WalletActivity

class WalletController {

    internal companion object {
        internal const val FILTER_ACTION_RESULT = "FILTER_ACTION_RESULT"
    }

    private var listener: WalletControllerListener? = null
    private var context: Context? = null

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val sdkResult = intent.getParcelableExtra<WalletSdkResult>(SdkResult.EXTRA_SDK_RESULT)
            when(sdkResult){
                is WalletSdkResult.DidShowWebWallet -> listener?.didShowManageWebWallet()
            }
        }
    }

    /**
     * S'inscris au broadcast qui permet de communiquer avec la webView
     *
     * @param listener [PaymentControllerListener] utilisé pour la réception du résultat de la webView
     * @param context context utilisé à la création de PaymentController
     */
    fun registerListener(listener: WalletControllerListener, context: Context) {
        this.listener = listener
        this.context = context
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, IntentFilter(WalletController.FILTER_ACTION_RESULT))
    }

    /**
     * Se désinscris au broadcast qui permet de communiquer avec la webView
     */
    fun unregisterListener() {
        this.listener = null
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(broadcastReceiver)
        }
        this.context = null
    }

    /**
     * Affiche le porte-monnaie
     *
     * @param uri uri qui redirige la webView vers le porte-monnaie
     */
    fun showManageWallet(uri: Uri) {
        //LocalBroadcastManager.getInstance()
        val c = context ?: return
        val intent = WalletActivity.buildIntent(c, uri)
        c.startActivity(intent)
    }
}
