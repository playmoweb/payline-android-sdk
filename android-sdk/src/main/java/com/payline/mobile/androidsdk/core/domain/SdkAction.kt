package com.payline.mobile.androidsdk.core.domain

import android.os.Parcelable

internal interface SdkAction: Parcelable {

    companion object {

        /**
         * The action to filter on
         */
        const val BROADCAST_SDK_ACTION = "BROADCAST_SDK_ACTION"

        /**
         * The SdkAction object
         */
        const val EXTRA_SDK_ACTION = "ExtraScriptAction"
    }
}