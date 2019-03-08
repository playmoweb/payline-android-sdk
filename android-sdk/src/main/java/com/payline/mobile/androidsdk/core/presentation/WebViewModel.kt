package com.payline.mobile.androidsdk.core.presentation

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.payline.mobile.androidsdk.core.domain.web.ScriptEvent
import com.payline.mobile.androidsdk.core.domain.web.ScriptHandler
import com.payline.mobile.androidsdk.core.domain.SdkResult
import com.payline.mobile.androidsdk.core.data.WidgetState
import com.payline.mobile.androidsdk.core.domain.SdkResultBroadcaster
import com.payline.mobile.androidsdk.payment.domain.PaymentSdkResult
import com.payline.mobile.androidsdk.wallet.domain.WalletSdkResult

internal class WebViewModel(app: Application): AndroidViewModel(app), SdkResultBroadcaster {

    internal val scriptHandler = ScriptHandler {
        when (it) {
            is ScriptEvent.DidShowState -> didShowState(it)
            is ScriptEvent.FinalStateHasBeenReached -> finalStateHasBeenReached(it)
            is ScriptEvent.DidEndToken -> didEndToken()
        }
    }

    private fun didEndToken() {
        broadcast(PaymentSdkResult.DidFinishPaymentForm(WidgetState.PAYMENT_CANCELED))
        finishUi.postValue(true)
    }

    private fun didShowState(event: ScriptEvent.DidShowState) {
        when(event.state) {

            WidgetState.PAYMENT_METHODS_LIST -> {
                broadcast(PaymentSdkResult.DidShowPaymentForm())
            }
            WidgetState.PAYMENT_REDIRECT_NO_RESPONSE -> {
                // TODO: hide button
            }
            WidgetState.MANAGE_WEB_WALLET -> {
                broadcast(WalletSdkResult.DidShowWebWallet())
            }
            WidgetState.PAYMENT_METHOD_NEEDS_MORE_INFOS,
            WidgetState.ACTIVE_WAITING,
            WidgetState.PAYMENT_CANCELED_WITH_RETRY,
            WidgetState.PAYMENT_FAILURE_WITH_RETRY -> {
                // TODO: anything?
            }
        }
    }

    private fun finalStateHasBeenReached(event: ScriptEvent.FinalStateHasBeenReached) {
        broadcast(PaymentSdkResult.DidFinishPaymentForm(event.state))
        finishUi.postValue(true)
    }

    val isLoading = MutableLiveData<Boolean>().apply {
        value = true
    }

    val finishUi = MutableLiveData<Boolean>().apply {
        value = false
    }

    override fun broadcast(result: SdkResult) {
        LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(
                    SdkResult.EXTRA_SDK_RESULT,
                    result
                )
            }
        )
    }

}
