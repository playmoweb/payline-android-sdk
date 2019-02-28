package com.myluckyday.test.paylinesdk.payment.domain

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

    data class GetContextInfo(val key: String?): PaymentScriptAction() {

        override val command: String
            get() {
                val k = key ?: "null"
                return ScriptAction.commandWrapper("getContextInfo('$k')")
            }
    }

    object FinalizeShortCut: PaymentScriptAction() {

        override val command: String
            get() = ScriptAction.commandWrapper("finalizeShortCut()")
    }

    object GetBuyerShortCut: PaymentScriptAction() {

        override val command: String
            get() = ScriptAction.commandWrapper("getBuyerShortCut()")
    }

}
