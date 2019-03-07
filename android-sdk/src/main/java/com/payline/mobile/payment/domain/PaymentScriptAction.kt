package com.payline.mobile.payment.domain

import com.payline.mobile.core.data.ContextInfoKey
import com.payline.mobile.core.domain.ScriptAction
import org.json.JSONObject

internal sealed class PaymentScriptAction: ScriptAction {

    data class UpdateWebPaymentData(val paymentData: JSONObject): PaymentScriptAction() {

        override val command: String
            get() = ScriptAction.commandWrapper("updateWebPaymentData('$paymentData')")
    }

    object IsSandbox: PaymentScriptAction() {

        override val command: String
            get() = ScriptAction.commandWrapper("isSandbox()")
    }

    data class EndToken(val handledByMerchant: Boolean, val additionalData: JSONObject): PaymentScriptAction() {

        override val command: String
            get() {
                val jsCallback = "function() { PaylineSdkAndroid.didEndToken(); }"
                val comm = "endToken('$additionalData', $jsCallback, null, $handledByMerchant)"
                return ScriptAction.commandWrapper(comm)
            }
    }

    object GetLanguage: PaymentScriptAction() {

        override val command: String
            get() = ScriptAction.commandWrapper("getLanguage()")
    }

    data class GetContextInfo(val key: ContextInfoKey): PaymentScriptAction() {

        override val command: String
            get() = ScriptAction.commandWrapper("getContextInfo('${key.value}')")
    }
}
