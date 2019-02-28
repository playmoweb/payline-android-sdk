package com.myluckyday.test.paylinesdk.app.presentation

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.myluckyday.test.paylinesdk.app.domain.ScriptEvent
import com.myluckyday.test.paylinesdk.app.domain.ScriptHandler
import com.myluckyday.test.paylinesdk.app.domain.SdkResult
import com.myluckyday.test.paylinesdk.app.domain.WidgetState
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult
import com.myluckyday.test.paylinesdk.wallet.domain.WalletSdkResult

internal class WebViewModel(app: Application): AndroidViewModel(app) {

    internal val scriptHandler = ScriptHandler {

        when(it) {
            is ScriptEvent.DidShowState -> {

                when(it.state) {

                    WidgetState.PAYMENT_METHODS_LIST -> {
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidShowPaymentForm())
                            }
                        )
                    }
                    WidgetState.MANAGE_WEB_WALLET -> {
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, WalletSdkResult.DidShowWebWallet())
                            }
                        )
                    }
                }
            }
        }
    }

    internal fun cancelCurrentOperation() {
        // TODO:
    }

    val uri = MutableLiveData<Uri>()

}
