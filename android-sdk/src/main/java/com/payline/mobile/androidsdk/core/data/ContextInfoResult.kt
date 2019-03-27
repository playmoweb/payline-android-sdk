package com.payline.mobile.androidsdk.core.data

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray

/**
 * Cette classe va être utilisée pour traiter le résultat obtenu par la wevView
 * Trois types de données pourront être reçus : Int, String, ObjectArray
 *
 * @link <https://support.payline.com/hc/fr/articles/360017949833-PW-API-JavaScript>
 */
sealed class ContextInfoResult: Parcelable {

    /**
     * Cette classe traitera un résultat obtenu par la webView de type Int
     *
     * @param key key correspond à la clé de l'information que l'on obtient
     * @param value value correspond à l'information de type Int que l'on obtient
     */
    class Int(val key: ContextInfoKey, val value: kotlin.Int): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
            dest.writeString(key.name)
            dest.writeInt(value)
        }

        private constructor(parcel: Parcel): this(ContextInfoKey.valueOf(parcel.readString()!!), parcel.readInt())

        override fun describeContents(): kotlin.Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<Int> {
                override fun createFromParcel(source: Parcel): Int = Int(source)
                override fun newArray(size: kotlin.Int): Array<Int?> = arrayOfNulls(size)
            }
        }
    }

    /**
     * Cette classe traitera un résultat obtenu par la webView de type String
     *
     * @param key key correspond à la clé de l'information que l'on obtient
     * @param value value correspond à l'information de type String que l'on obtient
     */
    class String(val key: ContextInfoKey, val value: kotlin.String): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
            dest.writeString(key.name)
            dest.writeString(value)
        }

        private constructor(parcel: Parcel): this(ContextInfoKey.valueOf(parcel.readString()!!), parcel.readString()!!)

        override fun describeContents(): kotlin.Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<String> {
                override fun createFromParcel(source: Parcel): String = String(source)
                override fun newArray(size: kotlin.Int): Array<String?> = arrayOfNulls(size)
            }
        }

    }

    /**
     * Cette classe traitera un résultat obtenu par la webView de type JSONArray
     *
     * @param key key correspond à la clé de l'information que l'on obtient
     * @param value value correspond à l'information de type JSONArray que l'on obtient
     */
    class ObjectArray(val key: ContextInfoKey, val value: JSONArray): ContextInfoResult() {

        override fun writeToParcel(dest: Parcel, flags: kotlin.Int) {
            dest.writeString(key.name)
            dest.writeString(value.toString())
        }

        private constructor(parcel: Parcel): this(ContextInfoKey.valueOf(parcel.readString()!!), JSONArray(parcel.readString()))

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
