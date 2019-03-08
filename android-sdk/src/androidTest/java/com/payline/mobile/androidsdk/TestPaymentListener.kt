package com.payline.mobile.androidsdk

import com.payline.mobile.androidsdk.core.data.ContextInfoResult
import com.payline.mobile.androidsdk.core.data.WidgetState
import com.payline.mobile.androidsdk.payment.PaymentControllerListener

class TestPaymentListener: PaymentControllerListener {

    var didShowPaymentForm = false
    var didFinishPaymentForm = false
    var didFinishPaymentFormState: WidgetState? = null
    var didGetIsSandbox = false
    var didGetLanguage = false
    var didGetContextInfo = false
    var didGetContextInfoResult: ContextInfoResult? = null

    override fun didShowPaymentForm() {
        didShowPaymentForm = true
    }

    override fun didFinishPaymentForm(state: WidgetState) {
        didFinishPaymentForm = true
        didFinishPaymentFormState = state
    }

    override fun didGetIsSandbox(isSandbox: Boolean) {
        didGetIsSandbox = true
    }

    override fun didGetLanguage(language: String) {
        didGetLanguage = true
    }

    override fun didGetContextInfo(info: ContextInfoResult) {
        didGetContextInfo = true
        didGetContextInfoResult = info
    }

}