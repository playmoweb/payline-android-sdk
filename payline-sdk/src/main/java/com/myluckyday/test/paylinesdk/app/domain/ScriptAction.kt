package com.myluckyday.test.paylinesdk.app.domain

internal interface ScriptAction {

    companion object {

        fun commandWrapper(command: String): String {
            return "Payline.Api.$command;"
        }
    }

    val command: String
}
