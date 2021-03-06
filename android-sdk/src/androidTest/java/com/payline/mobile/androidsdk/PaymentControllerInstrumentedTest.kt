package com.payline.mobile.androidsdk

import android.net.Uri
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.web.model.SimpleAtom
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.payline.mobile.androidsdk.core.data.ContextInfoKey
import com.payline.mobile.androidsdk.core.data.WidgetState
import com.payline.mobile.androidsdk.payment.PaymentController
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
import java.util.concurrent.TimeUnit.SECONDS

@RunWith(AndroidJUnit4::class)
class PaymentControllerInstrumentedTest {

    companion object {
        const val MAX_WAIT = 60L
    }

    private var paymentController: PaymentController? = null
    private var testListener: TestPaymentListener? = null

    @Rule @JvmField
    val blankActivityRule = ActivityTestRule<BlankActivity>(BlankActivity::class.java)

    @Before
    fun init() {
        paymentController = PaymentController()
        testListener = TestPaymentListener()
    }

    @Test
    fun didShowPaymentForm() {
        commonTestInitWebWidget()
    }

    @Test
    fun didFinishPaymentForm_success() {

        commonTestInitWebWidget()

        onWebView()
            .perform(SimpleAtom("PaylineSdkAndroid.finalStateHasBeenReached('{\"state\":\"${WidgetState.PAYMENT_SUCCESS.name}\"}');"))

        await.atMost(MAX_WAIT, SECONDS).until { testListener?.didFinishPaymentForm }
        Truth.assertThat(testListener?.didFinishPaymentFormState).isEqualTo(WidgetState.PAYMENT_SUCCESS)
    }

    @Test
    fun didFinishPaymentForm_failure() {

        commonTestInitWebWidget()

        onWebView()
            .perform(SimpleAtom("PaylineSdkAndroid.finalStateHasBeenReached('{\"state\":\"${WidgetState.PAYMENT_FAILURE.name}\"}');"))

        await.atMost(MAX_WAIT, SECONDS).until { testListener?.didFinishPaymentForm }
        Truth.assertThat(testListener?.didFinishPaymentFormState).isEqualTo(WidgetState.PAYMENT_FAILURE)
    }

    @Test
    fun didFinishPaymentForm_cancelled_inWebView() {

        commonTestInitWebWidget()

        onWebView()
            .perform(SimpleAtom("PaylineSdkAndroid.finalStateHasBeenReached('{\"state\":\"${WidgetState.PAYMENT_CANCELED.name}\"}');"))

        await.atMost(MAX_WAIT, SECONDS).until { testListener?.didFinishPaymentForm }
        Truth.assertThat(testListener?.didFinishPaymentFormState).isEqualTo(WidgetState.PAYMENT_CANCELED)
    }

    @Test
    fun didFinishPaymentForm_cancelled_byButtonClick() {

        commonTestInitWebWidget()

        onView(withId(R.id.b_cancel_payment_activity))
            .perform(click())

        await.atMost(MAX_WAIT, SECONDS).until { testListener?.didFinishPaymentForm }
        Truth.assertThat(testListener?.didFinishPaymentFormState).isEqualTo(WidgetState.PAYMENT_CANCELED)
    }

    @Test
    fun endToken() {

        commonTestInitWebWidget()

        paymentController?.endToken(false, null)

        await.atMost(MAX_WAIT, SECONDS).until { testListener?.didFinishPaymentForm }
        Truth.assertThat(testListener?.didFinishPaymentFormState).isEqualTo(WidgetState.PAYMENT_CANCELED)
    }

    @Test
    fun isSandbox() {
        commonTestInitWebWidget()
        paymentController?.getIsSandbox()
        await.atMost(MAX_WAIT, SECONDS).until { testListener?.didGetIsSandbox }
    }

    @Test
    fun getLanguageCode() {
        commonTestInitWebWidget()
        paymentController?.getLanguageCode()
        await.atMost(MAX_WAIT, SECONDS).until { testListener?.didGetLanguageCode }
    }

    @Test
    fun getContextInfo_currencyCode() {
        commonTestInitWebWidget()
        paymentController?.getContextInfo(ContextInfoKey.CURRENCY_CODE)
        await.atMost(MAX_WAIT, SECONDS).until { testListener?.didGetContextInfo }
    }

    private fun commonTestInitWebWidget() {

        val activity = blankActivityRule.activity

        paymentController!!.registerListener(testListener!!, activity)

        var result: FetchTokenResult? = null
        val callback: (FetchTokenResult?)->Unit = {
            result = it
        }
        val fetch = TokenFetcher(callback)
        fetch.execute(FetchTokenParams.testPaymentParams())
        await.atMost(MAX_WAIT, SECONDS).untilNotNull { result }

        Truth.assertThat(result?.redirectUrl).isNotNull()

        val uri = Uri.parse(result!!.redirectUrl)
        paymentController?.showPaymentForm(uri)

        await.atMost(MAX_WAIT, SECONDS).until { testListener!!.didShowPaymentForm }
    }

    @After
    fun deinit() {
        paymentController?.unregisterListener()
        paymentController = null
        testListener = null
    }
}
