package com.myluckyday.test.paylinesdk.core.presentation

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.myluckyday.test.paylinesdk.core.domain.ScriptEvent
import com.myluckyday.test.paylinesdk.core.domain.ScriptHandler
import com.myluckyday.test.paylinesdk.core.domain.SdkResult
import com.myluckyday.test.paylinesdk.core.data.WidgetState
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
                    WidgetState.PAYMENT_REDIRECT_NO_RESPONSE -> {
                        Log.d("TAG", "PAYMENT_REDIRECT_NO_RESPONSE")
                        // TODO:
                    }
                    WidgetState.MANAGE_WEB_WALLET -> {
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, WalletSdkResult.DidShowWebWallet())
                            }
                        )
                    }
                    WidgetState.PAYMENT_METHOD_NEEDS_MORE_INFOS -> {
                        //TODO
                    }
                    WidgetState.ACTIVE_WAITING -> {
                        //TODO
                    }
                    WidgetState.PAYMENT_CANCELED_WITH_RETRY -> {
                        //TODO
                    }
                    WidgetState.PAYMENT_FAILURE_WITH_RETRY -> {
                        //TODO
                    }
                }
            }

            is ScriptEvent.FinalStateHasBeenReached -> {

                when(it.state) {

                    WidgetState.PAYMENT_CANCELED -> {
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidCancelPaymentForm())
                            }
                        )
                    }
                    WidgetState.PAYMENT_SUCCESS,
                    WidgetState.PAYMENT_FAILURE,
                    WidgetState.TOKEN_EXPIRED,
                    WidgetState.BROWSER_NOT_SUPPORTED,
                    WidgetState.PAYMENT_ONHOLD_PARTNER,
                    WidgetState.PAYMENT_SUCCESS_FORCE_TICKET_DISPLAY -> {
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidFinishPaymentForm())
                            }
                        )
                    }
                }
            }

            is ScriptEvent.DidEndToken -> {
                LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                    Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                        putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidCancelPaymentForm())
                    }
                )
                finishUi.postValue(true)
            }
        }
    }

    val finishUi = MutableLiveData<Boolean>().apply {
        value = false
    }

}
