package com.payline.mobile.androidsdk.core.domain.web

internal interface ScriptActionExecutor {
    fun executeAction(action: ScriptAction, callback: (String)->Unit)
}