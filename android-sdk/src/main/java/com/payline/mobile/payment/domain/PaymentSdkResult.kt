package com.payline.mobile.payment.domain

import android.os.Parcel
import android.os.Parcelable
import com.payline.mobile.core.data.ContextInfoResult
import com.payline.mobile.core.domain.SdkResult

internal sealed class PaymentSdkResult: SdkResult {

    class DidShowPaymentForm(): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            //The function doesn't have parameters
        }

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

        override fun writeToParcel(dest: Parcel, flags: Int) {
            //The function doesn't have parameters
        }

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

        override fun writeToParcel(dest: Parcel, flags: Int) {
            //The function doesn't have parameters
        }

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

    data class DidGetContextInfo(val result: ContextInfoResult): PaymentSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeParcelable(result, 0)
        }

        private constructor(parcel: Parcel): this(parcel.readParcelable<ContextInfoResult>(ContextInfoResult::class.java.classLoader))

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidGetContextInfo> {
                override fun createFromParcel(source: Parcel): DidGetContextInfo = DidGetContextInfo(source)
                override fun newArray(size: Int): Array<DidGetContextInfo?> = arrayOfNulls(size)
            }
        }
    }
}
