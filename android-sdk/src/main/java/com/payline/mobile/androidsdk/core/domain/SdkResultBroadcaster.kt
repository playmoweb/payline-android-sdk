package com.payline.mobile.androidsdk.core.domain

internal interface SdkResultBroadcaster {
    fun broadcast(result: SdkResult)
}