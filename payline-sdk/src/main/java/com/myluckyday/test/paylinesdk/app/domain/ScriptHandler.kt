package com.myluckyday.test.paylinesdk.app.javascript

import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebView

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
