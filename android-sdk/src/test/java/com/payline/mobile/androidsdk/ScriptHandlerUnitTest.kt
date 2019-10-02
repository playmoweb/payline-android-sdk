package com.payline.mobile.androidsdk

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.payline.mobile.androidsdk.core.data.WidgetState
import com.payline.mobile.androidsdk.core.domain.web.ScriptEvent
import com.payline.mobile.androidsdk.core.domain.web.ScriptHandler
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest= Config.NONE, sdk = [Build.VERSION_CODES.P])
class ScriptHandlerUnitTest {

    @Test
    fun scriptHandler_nameIsCorrect() {
        val sh = ScriptHandler {}
        Assert.assertEquals(sh.toString(), "PaylineSdkAndroid")
    }

    @Test
    fun scriptHandler_testDidEndToken() {

        val sh = ScriptHandler {
            Assert.assertTrue(it is ScriptEvent.DidEndToken)
        }

        sh.didEndToken()
    }

    @Test
    fun scriptHandler_testDidShowState_paymentMethodsList() {

        val sh = ScriptHandler {
            Assert.assertTrue(it is ScriptEvent.DidShowState)
            when (it) {
                is ScriptEvent.DidShowState -> {
                    Assert.assertEquals(it.state, WidgetState.PAYMENT_METHODS_LIST)
                }
                else -> Assert.fail("should recieve state PAYMENT_METHODS_LIST")
            }
        }

        sh.didShowState(scriptHandlerHelperBuildWidgetStateJson(WidgetState.PAYMENT_METHODS_LIST.name))
    }

    @Test
    fun scriptHandler_testDidShowState_manageWebWallet() {

        val sh = ScriptHandler {
            Assert.assertTrue(it is ScriptEvent.DidShowState)
            when (it) {
                is ScriptEvent.DidShowState -> {
                    Assert.assertEquals(it.state, WidgetState.MANAGE_WEB_WALLET)
                }
                else -> Assert.fail("should recieve state MANAGE_WEB_WALLET")
            }
        }

        sh.didShowState(scriptHandlerHelperBuildWidgetStateJson(WidgetState.MANAGE_WEB_WALLET.name))
    }

    @Test
    fun scriptHandler_testFinalStateHasBeenReached_paymentCanceled() {

        val sh = ScriptHandler {
            Assert.assertTrue(it is ScriptEvent.FinalStateHasBeenReached)
        }

        sh.finalStateHasBeenReached(scriptHandlerHelperBuildWidgetStateJson(WidgetState.PAYMENT_CANCELED.name))
        sh.finalStateHasBeenReached(scriptHandlerHelperBuildWidgetStateJson(WidgetState.PAYMENT_SUCCESS.name))
        sh.finalStateHasBeenReached(scriptHandlerHelperBuildWidgetStateJson(WidgetState.PAYMENT_FAILURE.name))
        sh.finalStateHasBeenReached(scriptHandlerHelperBuildWidgetStateJson(WidgetState.TOKEN_EXPIRED.name))
        sh.finalStateHasBeenReached(scriptHandlerHelperBuildWidgetStateJson(WidgetState.BROWSER_NOT_SUPPORTED.name))
        sh.finalStateHasBeenReached(scriptHandlerHelperBuildWidgetStateJson(WidgetState.PAYMENT_ONHOLD_PARTNER.name))
        sh.finalStateHasBeenReached(scriptHandlerHelperBuildWidgetStateJson(WidgetState.PAYMENT_SUCCESS_FORCE_TICKET_DISPLAY.name))
    }

    private fun scriptHandlerHelperBuildWidgetStateJson(widgetStateName: String): String {
        return JSONObject().apply {
            put("state", widgetStateName)
        }.toString()
    }
}