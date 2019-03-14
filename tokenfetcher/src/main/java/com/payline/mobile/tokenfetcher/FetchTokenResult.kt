package com.payline.mobile.tokenfetcher

data class FetchTokenResult(
    val type: FetchTokenParams.Type,
    val code: String,
    val message: String,
    val redirectUrl: String,
    val token: String
)