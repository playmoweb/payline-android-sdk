package com.myluckyday.test.paylinesdk

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.myluckyday.test.paylinesdk.payment.PaymentControllerListener
import com.myluckyday.test.paylinesdk.payment.presentation.PaymentActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScriptHandlerInstrumentedTest {

    @Test
    fun scriptHandler_testExecute() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), PaymentActivity::class.java)


    }
}