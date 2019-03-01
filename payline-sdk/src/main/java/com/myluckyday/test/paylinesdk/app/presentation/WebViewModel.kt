package com.myluckyday.test.paylinesdk.app.presentation

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.myluckyday.test.paylinesdk.app.domain.ScriptEvent
import com.myluckyday.test.paylinesdk.app.domain.ScriptHandler
import com.myluckyday.test.paylinesdk.app.domain.SdkResult
import com.myluckyday.test.paylinesdk.app.data.WidgetState
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
                    WidgetState.PAYMENT_FAILURE_WITH_RETRY -> {
                        Log.d("TAG", "PAYMENT_FAILURE_WITH_RETRY")
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidFinishPaymentForm())
                            }
                        )
                    }
                    WidgetState.PAYMENT_METHOD_NEEDS_MORE_INFOS -> {
                        Log.d("TAG", "PAYMENT_METHOD_NEEDS_MORE_INFOS")
                        // TODO:
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
                    WidgetState.ACTIVE_WAITING -> {
                        Log.d("TAG", "ACTIVE_WAITING")
                        // TODO:
                    }
                    WidgetState.PAYMENT_CANCELED_WITH_RETRY -> {
                        Log.d("TAG", "PAYMENT_CANCELED_WITH_RETRY")
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidCancelPaymentForm())
                            }
                        )
                    }
                }
            }

            is ScriptEvent.FinalStateHasBeenReached -> {

                when(it.state) {

                    WidgetState.PAYMENT_CANCELED -> {
                        Log.d("TAG", "PAYMENT_CANCELED")
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidShowPaymentForm())
                            }
                        )
                    }
                    WidgetState.PAYMENT_SUCCESS -> {
                        Log.d("TAG", "PAYMENT_SUCCESS")
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidFinishPaymentForm())
                            }
                        )
                    }
                    WidgetState.PAYMENT_FAILURE -> {
                        Log.d("TAG", "PAYMENT_FAILURE")
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidFinishPaymentForm())
                            }
                        )
                    }
                    WidgetState.TOKEN_EXPIRED -> {
                        Log.d("TAG", "TOKEN_EXPIRED")
                        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidFinishPaymentForm())
                            }
                        )
                    }
                    WidgetState.BROWSER_NOT_SUPPORTED -> {
                        Log.d("TAG", "BROWSER_NOT_SUPPORTED")
                        // TODO:
                    }
                    WidgetState.PAYMENT_ONHOLD_PARTNER -> {
                        Log.d("TAG", "PAYMENT_ONHOLD_PARTNER")
                        // TODO:
                    }
                    WidgetState.PAYMENT_SUCCESS_FORCE_TICKET_DISPLAY -> {
                        Log.d("TAG", "PAYMENT_SUCCESS_FORCE_TICKET_DISPLAY")
                        // TODO:
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
