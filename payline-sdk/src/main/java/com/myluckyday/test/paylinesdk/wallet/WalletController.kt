package com.myluckyday.test.paylinesdk.wallet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.myluckyday.test.paylinesdk.app.domain.SdkResult
import com.myluckyday.test.paylinesdk.wallet.domain.WalletSdkResult

class WalletController {

    internal companion object {
        internal const val FILTER_ACTION_RESULT = "FILTER_ACTION_RESULT"
    }

    private var listener: WalletControllerListener? = null

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val sdkResult = intent.getParcelableExtra<WalletSdkResult>(SdkResult.EXTRA_SDK_RESULT)
            when(sdkResult){
                is WalletSdkResult.DidShowWebWallet -> listener?.didShowManageWebWallet()
                is WalletSdkResult.DidFinishWebWallet -> listener?.didFinishManageWebWallet()
            }
        }
    }

    /**
     * S'inscris au broadcast qui permet de communiquer avec ... TODO
     */
    fun registerListener(listener: WalletControllerListener, context: Context) {
        this.listener = listener
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, IntentFilter(WalletController.FILTER_ACTION_RESULT))
    }

    /**
     * Se désinscris au broadcast qui permet de communiquer avec ... TODO
     */
    fun unregisterListener(context: Context) {
        this.listener = null
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver)
    }
}
