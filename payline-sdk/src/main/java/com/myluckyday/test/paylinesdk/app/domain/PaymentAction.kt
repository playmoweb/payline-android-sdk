package com.myluckyday.test.paylinesdk.app.javascript

import com.myluckyday.test.paylinesdk.app.data.PaymentData

sealed class PaymentAction: ScriptAction {

    data class updateWebPaymentData(val paymentData: PaymentData)

    object isSandbox

    object endToken

    object getLanguage

    data class getContextInfo(val key: String)

    object finalizeShortCut

    object getBuyerShortCut

}
