package com.myluckyday.test.paylinesdk.payment.domain

import android.os.Parcel
import android.os.Parcelable
import com.myluckyday.test.paylinesdk.app.domain.SdkResult
import org.json.JSONArray
import org.json.JSONObject

internal sealed class PaymentSdkResult: SdkResult {

    class DidShowPaymentForm(): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {}

        private constructor(parcel: Parcel): this()

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidShowPaymentForm> {
                override fun createFromParcel(source: Parcel): DidShowPaymentForm = DidShowPaymentForm(source)
                override fun newArray(size: Int): Array<DidShowPaymentForm?> = arrayOfNulls(size)
            }
        }
    }

    class DidCancelPaymentForm(): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {}

        private constructor(parcel: Parcel): this()

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidCancelPaymentForm> {
                override fun createFromParcel(source: Parcel): DidCancelPaymentForm = DidCancelPaymentForm(source)
                override fun newArray(size: Int): Array<DidCancelPaymentForm?> = arrayOfNulls(size)
            }
        }
    }

    class DidFinishPaymentForm(): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {}

        private constructor(parcel: Parcel): this()

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidFinishPaymentForm> {
                override fun createFromParcel(source: Parcel): DidFinishPaymentForm = DidFinishPaymentForm(source)
                override fun newArray(size: Int): Array<DidFinishPaymentForm?> = arrayOfNulls(size)
            }
        }
    }

    data class DidGetIsSandbox(val isSandbox: Boolean): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeInt(if(isSandbox) 1 else 0)
        }

        private constructor(parcel: Parcel): this(parcel.readInt() == 1)

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidGetIsSandbox> {
                override fun createFromParcel(source: Parcel): DidGetIsSandbox = DidGetIsSandbox(source)
                override fun newArray(size: Int): Array<DidGetIsSandbox?> = arrayOfNulls(size)
            }
        }
    }

    data class DidGetLanguage(val language: String): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(language)
        }

        private constructor(parcel: Parcel): this(parcel.readString()!!)

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidGetLanguage> {
                override fun createFromParcel(source: Parcel): DidGetLanguage = DidGetLanguage(source)
                override fun newArray(size: Int): Array<DidGetLanguage?> = arrayOfNulls(size)
            }
        }
    }

    data class DidGetContextInfoObject(val contextInfo: JSONObject): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(contextInfo.toString())
        }

        private constructor(parcel: Parcel): this(JSONObject(parcel.readString()))

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidGetContextInfoObject> {
                override fun createFromParcel(source: Parcel): DidGetContextInfoObject = DidGetContextInfoObject(source)
                override fun newArray(size: Int): Array<DidGetContextInfoObject?> = arrayOfNulls(size)
            }
        }
    }

    data class DidGetContextInfoArray(val contextInfo: JSONArray): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(contextInfo.toString())
        }

        private constructor(parcel: Parcel): this(JSONArray(parcel.readString()))

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidGetContextInfoArray> {
                override fun createFromParcel(source: Parcel): DidGetContextInfoArray = DidGetContextInfoArray(source)
                override fun newArray(size: Int): Array<DidGetContextInfoArray?> = arrayOfNulls(size)
            }
        }
    }

    data class DidGetBuyerShortCut(val buyer: JSONObject): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(buyer.toString())
        }

        private constructor(parcel: Parcel): this(JSONObject(parcel.readString()))

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidGetBuyerShortCut> {
                override fun createFromParcel(source: Parcel): DidGetBuyerShortCut = DidGetBuyerShortCut(source)
                override fun newArray(size: Int): Array<DidGetBuyerShortCut?> = arrayOfNulls(size)
            }
        }
    }
}
