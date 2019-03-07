package com.payline.mobile.tokenfetcher

data class FetchTokenResult(
    val code: String,
    val message: String,
    val redirectUrl: String,
    val token: String
)