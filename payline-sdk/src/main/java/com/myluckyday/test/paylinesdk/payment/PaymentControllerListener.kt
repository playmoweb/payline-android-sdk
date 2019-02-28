package com.myluckyday.test.paylinesdk.payment

import com.myluckyday.test.paylinesdk.app.util.Either
import org.json.JSONArray
import org.json.JSONObject

interface PaymentControllerListener {

    /**
     * La liste des moyens de paiment a été affiché
     */
    fun didShowPaymentForm()
    fun didCancelPaymentForm()
    fun didFinishPaymentForm()
    fun didGetIsSandbox(isSandbox: Boolean)
    fun didGetLanguage(language: String)
    fun didGetContextInfo(info: Either<JSONObject, JSONArray>)
    fun didGetBuyerShortCut(buyer: JSONObject)

}
