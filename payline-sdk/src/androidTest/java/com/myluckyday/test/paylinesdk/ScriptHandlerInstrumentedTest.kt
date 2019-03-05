package com.myluckyday.test.paylinesdk

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.myluckyday.test.paylinesdk.core.data.ContextInfoKey
import com.myluckyday.test.paylinesdk.core.data.ContextInfoResult
import com.myluckyday.test.paylinesdk.core.domain.SdkResult
import com.myluckyday.test.paylinesdk.payment.PaymentController
import com.myluckyday.test.paylinesdk.payment.PaymentControllerListener
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult
import com.myluckyday.test.paylinesdk.payment.presentation.PaymentActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScriptHandlerInstrumentedTest: PaymentControllerListener{

    var paymentController = PaymentController()
    private lateinit var paymentActivity: PaymentActivity
    private var paymentFormState = 0

    @get:Rule
    private var activityTestRule = ActivityTestRule(PaymentActivity::class.java)

    @Before
    fun init(){
        paymentController = PaymentController()
        paymentActivity = activityTestRule.launchActivity(null)
        paymentController.registerListener(this, paymentActivity)
    }

    @Test
    fun scriptHandler_testGetLanguage() {

        LocalBroadcastManager.getInstance(paymentActivity!!).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidGetLanguage("fr"))
            }
        )

    }

    @Test
    fun scriptHandler_testIsSandbox() {

        LocalBroadcastManager.getInstance(paymentActivity!!).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidGetIsSandbox(true))
            }
        )

    }

    @Test
    fun scriptHandler_testContextInfo() {

        LocalBroadcastManager.getInstance(paymentActivity!!).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidGetContextInfo(ContextInfoResult.Int(ContextInfoKey.AMOUNT_SMALLEST_UNIT, "6".toInt())))
            }
        )
    }

    @Test
    fun scriptHandler_testShowPaymentForm() {

        LocalBroadcastManager.getInstance(paymentActivity!!).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidShowPaymentForm())
            }
        )

        Thread.sleep(5)

        Assert.assertEquals(1, paymentFormState)
    }

    @Test
    fun scriptHandler_testCancelPaymentForm() {

        LocalBroadcastManager.getInstance(paymentActivity!!).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidCancelPaymentForm())
            }
        )

        Thread.sleep(5)

        Assert.assertEquals(2, paymentFormState)
    }

    @Test
    fun scriptHandler_testFinishPaymentForm() {

        LocalBroadcastManager.getInstance(paymentActivity!!).sendBroadcast(
            Intent(SdkResult.BROADCAST_SDK_RESULT).apply {
                putExtra(SdkResult.EXTRA_SDK_RESULT, PaymentSdkResult.DidFinishPaymentForm())
            }
        )

        Thread.sleep(5)

        Assert.assertEquals(3, paymentFormState)
    }

    override fun didShowPaymentForm() {
        paymentFormState = 1
    }

    override fun didCancelPaymentForm() {
        paymentFormState = 2
    }

    override fun didFinishPaymentForm() {
        paymentFormState = 3
    }

    override fun didGetIsSandbox(isSandbox: Boolean) {
        Assert.assertTrue(isSandbox)
    }

    override fun didGetLanguage(language: String) {
        Assert.assertEquals("fr", language)
    }

    override fun didGetContextInfo(info: ContextInfoResult) {
        val contextInfo = (info as ContextInfoResult.Int)
        Assert.assertEquals(contextInfo.key, ContextInfoKey.AMOUNT_SMALLEST_UNIT)
        Assert.assertEquals(contextInfo.value, 6)
    }
}
