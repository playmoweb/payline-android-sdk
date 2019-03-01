package com.myluckyday.test.paylinesdk.app.util

import android.os.Parcel
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal sealed class ParcelDelegate<T>: ReadWriteProperty<Parcel, T> {

    class String: ParcelDelegate<kotlin.String>() {

        override fun getValue(thisRef: Parcel, property: KProperty<*>): kotlin.String {
            return thisRef.readString()!!
        }

        override fun setValue(thisRef: Parcel, property: KProperty<*>, value: kotlin.String) {
            thisRef.writeString(value)
        }
    }
}