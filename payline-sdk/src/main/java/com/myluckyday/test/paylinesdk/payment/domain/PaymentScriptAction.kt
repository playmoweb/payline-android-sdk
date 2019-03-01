package com.myluckyday.test.paylinesdk.payment.domain

import com.myluckyday.test.paylinesdk.app.data.ContextInfoKey
import com.myluckyday.test.paylinesdk.app.domain.ScriptAction
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

    data class EndToken(val handledByMerchant: Boolean): PaymentScriptAction() {

        override val command: String
            get() {
                // TODO: help!
                return ScriptAction.commandWrapper("endToken('$handledByMerchant', 'null', 'PaylineSdkAndroid.endToken')")
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
