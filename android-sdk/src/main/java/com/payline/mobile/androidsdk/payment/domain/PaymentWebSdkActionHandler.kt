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

    override fun handle(action: SdkAction, scriptExecutor: ScriptActionExecutor, broadcaster: SdkResultBroadcaster) {

        if(action !is PaymentSdkAction) return

        when(action) {
            is PaymentSdkAction.GetLanguage -> {
                scriptExecutor.executeScriptAction(PaymentScriptAction.GetLanguage) {
                    broadcaster.broadcast(
                        PaymentSdkResult.DidGetLanguage(
                            it
                        )
                    )
                }
            }
            is PaymentSdkAction.IsSandbox -> {
                scriptExecutor.executeScriptAction(PaymentScriptAction.IsSandbox) {
                    broadcaster.broadcast(
                        PaymentSdkResult.DidGetIsSandbox(
                            it.toBoolean()
                        )
                    )
                }
            }
            is PaymentSdkAction.GetContextInfo -> {
                scriptExecutor.executeScriptAction(
                    PaymentScriptAction.GetContextInfo(
                        action.key
                    )
                ) {
                    val parsed = when (action.key) {
                        ContextInfoKey.AMOUNT_SMALLEST_UNIT, ContextInfoKey.CURRENCY_DIGITS -> ContextInfoResult.Int(
                            action.key,
                            it.toInt()
                        )

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
            is PaymentSdkAction.EndToken -> {
                scriptExecutor.executeScriptAction(
                    PaymentScriptAction.EndToken(
                        action.handledByMerchant,
                        action.additionalData
                    ),
                    {}
                )
            }
        }
    }
}