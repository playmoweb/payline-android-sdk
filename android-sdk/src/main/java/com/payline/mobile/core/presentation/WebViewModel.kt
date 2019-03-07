package com.payline.mobile.core.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payline.mobile.core.domain.ScriptEvent
import com.payline.mobile.core.domain.ScriptHandler
import com.payline.mobile.core.domain.SdkResult
import com.payline.mobile.core.data.WidgetState
import com.payline.mobile.payment.domain.PaymentSdkResult
import com.payline.mobile.wallet.domain.WalletSdkResult

internal class WebViewModel(app: Application): AndroidViewModel(app) {

    internal val scriptHandler = ScriptHandler {

        when(it) {
            is ScriptEvent.DidShowState -> didShowState(it)

            is ScriptEvent.FinalStateHasBeenReached -> finalStateHasBeenReached(it)

            is ScriptEvent.DidEndToken -> didEndToken()
        }
    }

    private fun didEndToken() {
        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidCancelPaymentForm())
            }
        )
        finishUi.postValue(true)
    }

    private fun didShowState(event: ScriptEvent.DidShowState) {
        when (event.state) {

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

    private fun finalStateHasBeenReached(event: ScriptEvent.FinalStateHasBeenReached) {

        when (event.state) {

            WidgetState.PAYMENT_CANCELED -> {
                LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
                    Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                        putExtra(
                            SdkResult.EXTRA_SDK_RESULT,
                            PaymentSdkResult.DidCancelPaymentForm()
                        )
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
                        putExtra(
                            SdkResult.EXTRA_SDK_RESULT,
                            PaymentSdkResult.DidFinishPaymentForm()
                        )
                    }
                )
            }
        }
    }

    val finishUi = MutableLiveData<Boolean>().apply {
        value = false
    }

}
