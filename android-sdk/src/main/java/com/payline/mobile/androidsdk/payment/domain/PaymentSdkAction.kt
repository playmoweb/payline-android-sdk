package com.payline.mobile.androidsdk.payment.domain

import android.os.Parcel
import android.os.Parcelable
import com.payline.mobile.androidsdk.core.data.ContextInfoKey
import com.payline.mobile.androidsdk.core.domain.SdkAction
import org.json.JSONObject

internal sealed class PaymentSdkAction: SdkAction {

    data class UpdateWebPaymentData(val paymentData: JSONObject): PaymentSdkAction() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(paymentData.toString())
        }

        private constructor(parcel: Parcel): this(JSONObject(parcel.readString()))

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<UpdateWebPaymentData> {
                override fun createFromParcel(source: Parcel): UpdateWebPaymentData = UpdateWebPaymentData(source)
                override fun newArray(size: Int): Array<UpdateWebPaymentData?> = arrayOfNulls(size)
            }
        }
    }

    class IsSandbox(): PaymentSdkAction() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            //The function doesn't have parameters
        }

        private constructor(parcel: Parcel): this()

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<IsSandbox> {
                override fun createFromParcel(source: Parcel): IsSandbox = IsSandbox(source)
                override fun newArray(size: Int): Array<IsSandbox?> = arrayOfNulls(size)
            }
        }
    }

    class EndToken(val handledByMerchant: Boolean, additionalData: String?): PaymentSdkAction() {

        val additionalData: String = additionalData ?: ""

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeInt(if(handledByMerchant) 1 else 0)
            dest.writeString(additionalData)
        }

        private constructor(parcel: Parcel): this(parcel.readInt() == 1, parcel.readString())

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<EndToken> {
                override fun createFromParcel(source: Parcel): EndToken = EndToken(source)
                override fun newArray(size: Int): Array<EndToken?> = arrayOfNulls(size)
            }
        }
    }

    class GetLanguageCode(): PaymentSdkAction() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            //The function doesn't have parameters
        }

        private constructor(parcel: Parcel): this()

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<GetLanguageCode> {
                override fun createFromParcel(source: Parcel): GetLanguageCode = GetLanguageCode(source)
                override fun newArray(size: Int): Array<GetLanguageCode?> = arrayOfNulls(size)
            }
        }
    }

    data class GetContextInfo(val key: ContextInfoKey): PaymentSdkAction() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(key.name)
        }

        private constructor(parcel: Parcel): this(ContextInfoKey.valueOf(parcel.readString()))

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<GetContextInfo> {
                override fun createFromParcel(source: Parcel): GetContextInfo = GetContextInfo(source)
                override fun newArray(size: Int): Array<GetContextInfo?> = arrayOfNulls(size)
            }
        }
    }
}
