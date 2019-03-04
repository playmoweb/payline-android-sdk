package com.myluckyday.test.paylinesdk.core.util

import android.content.Intent
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal sealed class IntentExtraDelegate<T>(protected val key: String): ReadWriteProperty<Intent, T> {

    class Uri(key: String): IntentExtraDelegate<android.net.Uri?>(key) {
        override fun getValue(thisRef: Intent, property: KProperty<*>): android.net.Uri? = thisRef.getParcelableExtra(key)
        override fun setValue(thisRef: Intent, property: KProperty<*>, value: android.net.Uri?) {
            thisRef.putExtra(key, value)
        }
    }

    class PaymentSdkResult(key: String): IntentExtraDelegate<com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult>(key) {
        override fun getValue(thisRef: Intent, property: KProperty<*>): com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult {
            return thisRef.getParcelableExtra(key)
        }

        override fun setValue(thisRef: Intent, property: KProperty<*>, value: com.myluckyday.test.paylinesdk.payment.domain.PaymentSdkResult) {
            thisRef.putExtra(key, value)
        }
    }
}