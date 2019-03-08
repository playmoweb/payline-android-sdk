package com.payline.mobile.androidsdk

import com.payline.mobile.androidsdk.core.data.ContextInfoResult
import com.payline.mobile.androidsdk.payment.PaymentControllerListener

class TestListener: PaymentControllerListener {

    var didShowPaymentForm = false
    var didCancelPaymentForm = false
    var didFinishPaymentForm = false
    var didGetIsSandbox: Boolean? = null
    var didGetLanguage: String? = null
    var didGetContextInfo: ContextInfoResult? = null

    override fun didShowPaymentForm() {
        didShowPaymentForm = true
    }

    override fun didCancelPaymentForm() {
        didCancelPaymentForm = true
    }

    override fun didFinishPaymentForm() {
        didFinishPaymentForm = true
    }

    override fun didGetIsSandbox(isSandbox: Boolean) {
        didGetIsSandbox = isSandbox
    }

    override fun didGetLanguage(language: String) {
        didGetLanguage = language
    }

    override fun didGetContextInfo(info: ContextInfoResult) {
        didGetContextInfo = info
    }

}