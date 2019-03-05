package com.myluckyday.test.paylinesdk.wallet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.myluckyday.test.paylinesdk.core.domain.SdkResult
import com.myluckyday.test.paylinesdk.wallet.domain.WalletSdkResult
import com.myluckyday.test.paylinesdk.wallet.presentation.WalletActivity

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
     */
    fun registerListener(listener: WalletControllerListener, context: Context) {
        this.listener = listener
        this.context = context
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, IntentFilter(WalletController.FILTER_ACTION_RESULT))
    }

    /**
     * Se désinscris au broadcast qui permet de communiquer avec la webView
     */
    fun unregisterListener(context: Context) {
        this.listener = null
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver)
    }

    /**
     * Affiche le porte-monnaie
     */
    fun showManageWallet(token: String, uri: Uri){
        //LocalBroadcastManager.getInstance()
        val c = context ?: return
        val intent = WalletActivity.buildIntent(c, uri)
        c.startActivity(intent)
    }
}
