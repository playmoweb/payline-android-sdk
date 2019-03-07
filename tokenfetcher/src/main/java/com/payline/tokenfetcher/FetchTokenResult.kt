package com.payline.tokenfetcher

data class FetchTokenResult(
    val code: String,
    val message: String,
    val redirectUrl: String,
    val token: String
)