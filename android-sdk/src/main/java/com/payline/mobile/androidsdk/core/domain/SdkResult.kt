package com.payline.mobile.androidsdk.core.domain

import android.os.Parcelable

internal interface SdkResult: Parcelable {
    companion object {

        const val BROADCAST_SDK_RESULT = "BROADCAST_SDK_RESULT"
        const val EXTRA_SDK_RESULT = "EXTRA_SDK_RESULT"
    }
}