package com.myluckyday.test.payline_android.data

data class FetchTokenResult(
    val code: String,
    val message: String,
    val redirectUrl: String,
    val token: String
)