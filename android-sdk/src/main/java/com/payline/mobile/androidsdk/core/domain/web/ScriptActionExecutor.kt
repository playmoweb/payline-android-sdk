package com.payline.mobile.androidsdk.core.domain.web

internal interface ScriptActionExecutor {
    fun executeScriptAction(action: ScriptAction, callback: (String)->Unit)
}