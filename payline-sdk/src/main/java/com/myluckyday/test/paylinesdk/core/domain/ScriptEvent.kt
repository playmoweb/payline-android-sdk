package com.myluckyday.test.paylinesdk.core.domain

import android.os.Parcel
import android.os.Parcelable
import com.myluckyday.test.paylinesdk.core.data.WidgetState

internal sealed class ScriptEvent: Parcelable {

    data class DidShowState(val state: WidgetState) : ScriptEvent() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(state.name)
        }

        private constructor(parcel: Parcel): this(WidgetState.valueOf(parcel.readString()!!))

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidShowState> {
                override fun createFromParcel(source: Parcel): DidShowState = DidShowState(source)
                override fun newArray(size: Int): Array<DidShowState?> = arrayOfNulls(size)
            }
        }
    }

    data class DidEndToken(val code: Int): ScriptEvent() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeInt(code)
        }

        private constructor(parcel: Parcel): this(parcel.readInt())

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidEndToken> {
                override fun createFromParcel(source: Parcel): DidEndToken = DidEndToken(source)
                override fun newArray(size: Int): Array<DidEndToken?> = arrayOfNulls(size)
            }
        }
    }

    data class FinalStateHasBeenReached(val state: WidgetState): ScriptEvent() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(state.name)
        }

        private constructor(parcel: Parcel): this(WidgetState.valueOf(parcel.readString()!!))

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<FinalStateHasBeenReached> {
                override fun createFromParcel(source: Parcel): FinalStateHasBeenReached = FinalStateHasBeenReached(source)
                override fun newArray(size: Int): Array<FinalStateHasBeenReached?> = arrayOfNulls(size)
            }
        }
    }
}
