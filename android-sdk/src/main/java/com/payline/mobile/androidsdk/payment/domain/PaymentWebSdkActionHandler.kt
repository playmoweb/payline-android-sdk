package com.payline.mobile.androidsdk.payment.domain

import com.payline.mobile.androidsdk.core.data.ContextInfoKey
import com.payline.mobile.androidsdk.core.data.ContextInfoResult
import com.payline.mobile.androidsdk.core.domain.SdkAction
import com.payline.mobile.androidsdk.core.domain.SdkResultBroadcaster
import com.payline.mobile.androidsdk.core.domain.web.ScriptActionExecutor
import com.payline.mobile.androidsdk.core.domain.web.WebSdkActionDelegate
import org.json.JSONArray

internal object PaymentWebSdkActionHandler: WebSdkActionDelegate.Handler {

    override fun canHandleAction(action: SdkAction): Boolean {
        return action is PaymentSdkAction
    }

    private fun getLanguageCode(actionExecutor: ScriptActionExecutor, broadcaster: SdkResultBroadcaster) {
        actionExecutor.executeAction(PaymentScriptAction.GetLanguageCode) {
            broadcaster.broadcast(
                PaymentSdkResult.DidGetLanguageCode(
                    it
                )
            )
        }
    }

    private fun getIsSandbox(actionExecutor: ScriptActionExecutor, broadcaster: SdkResultBroadcaster) {
        actionExecutor.executeAction(PaymentScriptAction.IsSandbox) {
            broadcaster.broadcast(
                PaymentSdkResult.DidGetIsSandbox(
                    it.toBoolean()
                )
            )
        }
    }

    private fun getContextInfo(action: PaymentSdkAction.GetContextInfo, actionExecutor: ScriptActionExecutor, broadcaster: SdkResultBroadcaster) {
        actionExecutor.executeAction(PaymentScriptAction.GetContextInfo(action.key)) {

            val parsed = when (action.key) {

                ContextInfoKey.AMOUNT_SMALLEST_UNIT, ContextInfoKey.CURRENCY_DIGITS ->
                    ContextInfoResult.Int(action.key, it.toInt())

                ContextInfoKey.ORDER_DETAILS -> {
                    if (it == "null") {
                        ContextInfoResult.ObjectArray(
                            action.key,
                            JSONArray()
                        )
                    } else {
                        ContextInfoResult.ObjectArray(
                            action.key,
                            JSONArray(it)
                        )
                    }
                }
                else -> ContextInfoResult.String(action.key, it)
            }

            broadcaster.broadcast(
                PaymentSdkResult.DidGetContextInfo(
                    parsed
                )
            )
        }
    }

    private fun endToken(action: PaymentSdkAction.EndToken, actionExecutor: ScriptActionExecutor) {
        actionExecutor.executeAction(PaymentScriptAction.EndToken(action.handledByMerchant, action.additionalData)) {}
    }

    override fun handle(action: SdkAction, actionExecutor: ScriptActionExecutor, broadcaster: SdkResultBroadcaster) {

        if(action !is PaymentSdkAction) return

        when(action) {
            is PaymentSdkAction.GetLanguageCode -> getLanguageCode(actionExecutor, broadcaster)
            is PaymentSdkAction.IsSandbox -> getIsSandbox(actionExecutor, broadcaster)
            is PaymentSdkAction.GetContextInfo -> getContextInfo(action, actionExecutor, broadcaster)
            is PaymentSdkAction.EndToken -> endToken(action, actionExecutor)
        }
    }
}
