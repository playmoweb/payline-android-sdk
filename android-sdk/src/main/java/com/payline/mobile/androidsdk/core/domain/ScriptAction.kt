package com.payline.mobile.androidsdk.core.domain

internal interface ScriptAction {

    companion object {

        fun commandWrapper(command: String): String {
            return "Payline.Api.$command;"
        }
    }

    val command: String
}
