package com.myluckyday.test.paylinesdk.app

import android.webkit.JavascriptInterface

internal enum class WidgetState {
    PAYMENT_METHODS_LIST
}

internal data class ScriptHandler(private var listener: (WidgetState)->Unit) {

    @JavascriptInterface
    override fun toString(): String {
        return "PaylineSdkAndroid"
    }

    @JavascriptInterface
    fun didShowState(payload: Map<String,Any>) {
        val state = payload["state"] as? String ?: return
        val wState = WidgetState.valueOf(state)
        listener(wState)
    }

    @JavascriptInterface
    fun finalStateHasBeenReached(payload: Map<String,Any>) {

    }


}