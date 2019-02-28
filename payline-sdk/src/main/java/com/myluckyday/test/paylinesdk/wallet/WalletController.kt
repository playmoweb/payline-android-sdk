package com.myluckyday.test.paylinesdk.wallet

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class WalletController {

    internal companion object {
        internal const val FILTER_ACTION_RESULT = "FILTER_ACTION_RESULT"
    }

    private var listener: WalletControllerListener? = null

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // TODO: ??
        }
    }

    /**
     * TODO: doc
     */
    fun registerListener(listener: WalletControllerListener, context: Context) {
        this.listener = listener
        LocalBroadcastManager.getInstance(context).registerReceiver(broadcastReceiver, IntentFilter(WalletController.FILTER_ACTION_RESULT))
    }

    /**
     * TODO: doc
     */
    fun unregisterListener(context: Context) {
        this.listener = null
        LocalBroadcastManager.getInstance(context).unregisterReceiver(broadcastReceiver)
    }
}
