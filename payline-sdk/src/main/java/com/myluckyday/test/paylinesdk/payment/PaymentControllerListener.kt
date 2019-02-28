package com.myluckyday.test.paylinesdk.payment

import com.myluckyday.test.paylinesdk.app.util.ParcelableJsonElement
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
    fun didGetContextInfo(info: ParcelableJsonElement)
    fun didGetBuyerShortCut(buyer: JSONObject)

}
