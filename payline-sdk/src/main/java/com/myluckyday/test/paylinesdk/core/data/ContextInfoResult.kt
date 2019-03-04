package com.myluckyday.test.paylinesdk.core.data

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray

sealed class ContextInfoResult: Parcelable {

    class Int(val key: ContextInfoKey, val value: kotlin.Int): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
            dest.writeString(key.toString())
            dest.writeInt(value)
        }

        private constructor(parcel: Parcel): this(parcel.readInt())

        override fun describeContents(): kotlin.Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<Int> {
                override fun createFromParcel(source: Parcel): Int = Int(source)
                override fun newArray(size: kotlin.Int): Array<Int?> = arrayOfNulls(size)
            }
        }
    }

    class String(val key: ContextInfoKey, val value: kotlin.String): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
            dest.writeString(key.toString())
            dest.writeString(value)
        }

        private constructor(parcel: Parcel): this(parcel.readString()!!)

        override fun describeContents(): kotlin.Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<String> {
                override fun createFromParcel(source: Parcel): String = String(source)
                override fun newArray(size: kotlin.Int): Array<String?> = arrayOfNulls(size)
            }
        }

    }

    class ObjectArray(val key: ContextInfoKey, val value: JSONArray): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
            dest.writeString(key.toString())
            dest.writeString(value.toString())
        }

        private constructor(parcel: Parcel): this(JSONArray(parcel.readString()))

        override fun describeContents(): kotlin.Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<ObjectArray> {
                override fun createFromParcel(source: Parcel): ObjectArray = ObjectArray(source)
                override fun newArray(size: kotlin.Int): Array<ObjectArray?> = arrayOfNulls(size)
            }
        }
    }

}
