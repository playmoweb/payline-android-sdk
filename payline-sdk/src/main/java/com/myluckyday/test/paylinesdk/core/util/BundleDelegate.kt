package com.myluckyday.test.paylinesdk.core.util

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal sealed class BundleDelegate<T>(protected val key: String): ReadWriteProperty<Bundle, T> {

    class Uri(key: String): BundleDelegate<android.net.Uri?>(key) {
        override fun getValue(thisRef: Bundle, property: KProperty<*>): android.net.Uri? = thisRef.getParcelable(key)
        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: android.net.Uri?) = thisRef.putParcelable(key, value)
    }

}