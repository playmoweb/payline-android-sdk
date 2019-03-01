package com.myluckyday.test.payline_android.data

import org.json.JSONObject

data class FetchTokenParams(
    val type: FetchTokenParams.Type,
    val data: JSONObject
) {
    enum class Type {
        PAYMENT,
        WALLET
    }
}