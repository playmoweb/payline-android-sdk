package com.myluckyday.test.paylinesdk.app.javascript

sealed class ScriptEvent {

    data class didShowState(val payload: String) : ScriptEvent()

    data class finalStateHasBeenReached(val payload: String):ScriptEvent()

}
