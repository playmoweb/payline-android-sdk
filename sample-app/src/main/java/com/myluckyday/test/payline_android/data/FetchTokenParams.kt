package com.myluckyday.test.payline_android.data

data class FetchTokenParams(
    val orderRef: String,
    val amount: Int,
    val currencyCode: String
)