package com.myluckyday.test.paylinesdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.myluckyday.test.paylinesdk.core.domain.SdkAction
import com.myluckyday.test.paylinesdk.core.domain.SdkResult
import com.myluckyday.test.paylinesdk.core.presentation.WebViewModel
import com.myluckyday.test.paylinesdk.payment.PaymentController
import com.myluckyday.test.paylinesdk.payment.domain.PaymentScriptAction
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkAction
import com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult
import com.myluckyday.test.paylinesdk.payment.presentation.PaymentActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class PaymentTests {

    @get:Rule
    private var activityRule: ActivityTestRule<PaymentActivity> = ActivityTestRule(PaymentActivity::class.java)
    private val mReceivedIntents = ArrayList<Intent>()

    @Test
    fun getLanguage(){

        val paymentActivity = activityRule.launchActivity(null)
        val controller = PaymentController()

        val broadcastReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val sdkResult = intent.getParcelableExtra<PaymentSdkResult>(SdkResult.EXTRA_SDK_RESULT)
                mReceivedIntents.add(intent)
            }
        }
        LocalBroadcastManager.getInstance(paymentActivity).registerReceiver(broadcastReceiver, IntentFilter(SdkResult.BROADCAST_SDK_RESULT))

        controller.getLanguage()

        Assert.assertEquals(1, mReceivedIntents.size)
    }

}
