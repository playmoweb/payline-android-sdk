package com.myluckyday.test.paylinesdk.app.util

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONArray
import org.json.JSONObject

class ParcelableJsonElement: ParcelableEither<JSONObject,JSONArray> {

    enum class Type {
        OBJECT, ARRAY
    }

    private var type: Type = Type.OBJECT
    private var obj: JSONObject? = null
    private var array: JSONArray? = null

    constructor(obj: Any?) : super() {
        when(obj) {
            is JSONObject -> {
                this.type = Type.OBJECT
                this.obj = obj
            }
            is JSONArray -> {
                this.type = Type.ARRAY
                this.array = obj
            }
        }
    }

    override val one: JSONObject? = obj
    override val other: JSONArray? = array

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(type.name)
        when(type) {
            Type.OBJECT -> dest.writeString(obj.toString())
            Type.ARRAY -> dest.writeString(array.toString())
        }
    }

    private constructor(parcel: Parcel): this(if(parcel.constructorType == Type.OBJECT) JSONObject(parcel.jsonString) else JSONArray(parcel.jsonString))

    override fun describeContents(): Int = 0

    companion object {

        private var Parcel.constructorType by ParcelDelegate.ParcelableJsonElementType()
        private var Parcel.jsonString by ParcelDelegate.String()

        @JvmField
        val CREATOR = object: Parcelable.Creator<ParcelableJsonElement> {
            override fun createFromParcel(source: Parcel): ParcelableJsonElement = ParcelableJsonElement(source)
            override fun newArray(size: Int): Array<ParcelableJsonElement?> = arrayOfNulls(size)
        }
    }
}