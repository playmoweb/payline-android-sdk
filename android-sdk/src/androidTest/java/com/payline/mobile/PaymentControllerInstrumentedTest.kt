package com.payline.mobile

import android.net.Uri
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.payline.mobile.core.data.ContextInfoResult
import com.payline.mobile.paylinesdk.R
import com.payline.mobile.payment.PaymentController
import com.payline.mobile.payment.PaymentControllerListener
import com.payline.mobile.tokenfetcher.BlankActivity
import com.payline.mobile.tokenfetcher.FetchTokenParams
import com.payline.mobile.tokenfetcher.FetchTokenResult
import com.payline.mobile.tokenfetcher.TokenFetcher
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilNotNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit.*

class TestListener: PaymentControllerListener {

    var didShowPaymentForm = false
    var didCancelPaymentForm = false
    var didFinishPaymentForm = false
    var didGetIsSandbox: Boolean? = null
    var didGetLanguage: String? = null
    var didGetContextInfo: ContextInfoResult? = null

    override fun didShowPaymentForm() {
        didShowPaymentForm = true
    }

    override fun didCancelPaymentForm() {
        didCancelPaymentForm = true
    }

    override fun didFinishPaymentForm() {
        didFinishPaymentForm = true
    }

    override fun didGetIsSandbox(isSandbox: Boolean) {
        didGetIsSandbox = isSandbox
    }

    override fun didGetLanguage(language: String) {
        didGetLanguage = language
    }

    override fun didGetContextInfo(info: ContextInfoResult) {
        didGetContextInfo = info
    }

}

@RunWith(AndroidJUnit4::class)
class PaymentControllerInstrumentedTest {

    companion object {
        const val MAX_WAIT = 60L
    }

    private var paymentController: PaymentController? = null
    private var testListener: TestListener? = null

    @Rule @JvmField
    val blankActivityRule = ActivityTestRule<BlankActivity>(BlankActivity::class.java)

    @Before
    fun init() {
        paymentController = PaymentController()
        testListener = TestListener()
    }

    /**
     * Tests initialization of {@link PaymentController}
     * Calls {@link PaymentController#showPaymentForm(PaymentControllerDelegate, Context) showPaymentForm}
     * and verifies that {@link PaymentControllerDelegate#didShowPaymentForm() didShowPaymentForm} is called
     */
    @Test
    fun paymentController_didShowPaymentForm() {
        commonTestInitWebWidget()
    }

    @Test
    fun paymentController_didCancelPaymentForm() {

        commonTestInitWebWidget()

        onView(withId(R.id.b_cancel_payment_activity))
            .perform(click())

        await.atMost(MAX_WAIT, SECONDS).until { testListener!!.didCancelPaymentForm }
    }

    @Test
    fun paymentController_didFinishPaymentForm_success() {

        commonTestInitWebWidget()


    }

    @Test
    fun paymentController_endToken() {

        commonTestInitWebWidget()

        paymentController?.endToken(false, null)

        await.atMost(MAX_WAIT, SECONDS).until { testListener!!.didCancelPaymentForm }
    }

    private fun commonTestInitWebWidget() {

        val activity = blankActivityRule.activity

        paymentController!!.registerListener(testListener!!, activity)

        var result: FetchTokenResult? = null
        val fetch = TokenFetcher {
            result = it
        }
        fetch.execute(FetchTokenParams.testPaymentParams())
        await.atMost(MAX_WAIT, SECONDS).untilNotNull { result }

        Truth.assertThat(result?.redirectUrl).isNotNull()

        val uri = Uri.parse(result!!.redirectUrl)
        paymentController?.showPaymentForm(uri)

        await.atMost(MAX_WAIT, SECONDS).until { testListener!!.didShowPaymentForm }

        Truth.assertThat(testListener!!.didShowPaymentForm).isTrue()
    }

    @After
    fun deinit() {
        paymentController?.unregisterListener()
        paymentController = null
        testListener = null
    }
}
