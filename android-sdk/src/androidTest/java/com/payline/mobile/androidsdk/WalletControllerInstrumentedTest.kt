package com.payline.mobile.androidsdk

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth
import com.payline.mobile.androidsdk.wallet.WalletController
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
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class WalletControllerInstrumentedTest {

    companion object {
        const val MAX_WAIT = 60L
    }

    private var walletController: WalletController? = null
    private var testListener: TestWalletListener? = null

    @Rule
    @JvmField
    val blankActivityRule = ActivityTestRule<BlankActivity>(BlankActivity::class.java)

    @Before
    fun init() {
        walletController = WalletController()
        testListener = TestWalletListener()
    }

    @Test
    fun didShowWebWallet() {
        commonTestInitWebWidget()
    }

    private fun commonTestInitWebWidget() {

        val activity = blankActivityRule.activity

        walletController!!.registerListener(testListener!!, activity)

        var result: FetchTokenResult? = null
        val fetch = TokenFetcher {
            result = it
        }
        fetch.execute(FetchTokenParams.testPaymentParams())
        await.atMost(MAX_WAIT, TimeUnit.SECONDS).untilNotNull { result }

        Truth.assertThat(result?.redirectUrl).isNotNull()

        val uri = Uri.parse(result!!.redirectUrl)
        walletController?.manageWebWallet(uri)

        await.atMost(MAX_WAIT, TimeUnit.SECONDS).until { testListener!!.didShowManageWebWallet }
    }

    @After
    fun deinit() {
        walletController?.unregisterListener()
        walletController = null
        testListener = null
    }
}