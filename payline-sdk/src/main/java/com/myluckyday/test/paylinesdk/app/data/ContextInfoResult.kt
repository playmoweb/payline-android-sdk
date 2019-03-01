package com.myluckyday.test.paylinesdk.app.data

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray

sealed class ContextInfoResult: Parcelable {

    class Int(val value: kotlin.Int): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
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

    class String(val value: kotlin.String): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
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

    class ObjectArray(val value: JSONArray): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
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

//"PaylineAmountSmallestUnit": 100,
//"PaylineCurrencyDigits": 2,
//"PaylineCurrencyCode": "EUR",
//"PaylineBuyerFirstName": "Jean",
//"PaylineBuyerLastName": "DUPONT",
//"PaylineBuyerShippingAddress.street2": "Batiment 2",
//"PaylineBuyerShippingAddress.country": "FR",
//"PaylineBuyerShippingAddress.name": "Perso",
//"PaylineBuyerShippingAddress.street1": "5 rue de Marseille",
//"PaylineBuyerShippingAddress.cityName": "Aix",
//"PaylineBuyerShippingAddress.zipCode": "13390"
//"PaylineBuyerMobilePhone": "0605040302",
//"PaylineBuyerShippingAddress.phone": "0605040302",
//"PaylineBuyerIp": "127.126.125.100",
//"PaylineFormattedAmount": "EUR1.00",
//"PaylineOrderDate": "05/26/2008 10:00",
//"PaylineOrderRef": "170630111901",
//"PaylineOrderDeliveryMode": "4",
//"PaylineOrderDeliveryTime": "1",
//"PaylineOrderDetails": [
//{ "ref": "1",
//    "price": 998,
//    "quantity": 1,
//    "comment": "commentaire1",
//    "category": "1",
//    "brand": "66999",
//    "subcategory1": "",
//    "subcategory2": "",
//    "additionalData": ""
//}
//]