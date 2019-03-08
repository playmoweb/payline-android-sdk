package com.payline.mobile.androidsdk.core.domain.web

import com.payline.mobile.androidsdk.core.domain.SdkAction
import com.payline.mobile.androidsdk.core.domain.SdkResultBroadcaster
import com.payline.mobile.androidsdk.payment.domain.PaymentWebSdkActionHandler

internal class WebSdkActionDelegate(private val scriptExecutor: ScriptActionExecutor, private val broadcaster: SdkResultBroadcaster) {

    interface Handler {
        fun canHandleAction(action: SdkAction): Boolean
        fun handle(action: SdkAction, scriptExecutor: ScriptActionExecutor, broadcaster: SdkResultBroadcaster)
    }

    private val handlers = arrayOf<Handler>(
        PaymentWebSdkActionHandler
    )

    fun handleAction(action: SdkAction) {
        for(handler in handlers) {
            if(handler.canHandleAction(action)) {
                handler.handle(action, scriptExecutor, broadcaster)
            }
        }
    }
}