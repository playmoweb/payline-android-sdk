package com.myluckyday.test.paylinesdk.wallet.domain

import android.os.Parcel
import android.os.Parcelable
import com.myluckyday.test.paylinesdk.app.domain.SdkResult

internal sealed class WalletSdkResult: SdkResult {

    class DidShowWebWallet(): WalletSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {}

        private constructor(parcel: Parcel): this()

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidShowWebWallet> {
                override fun createFromParcel(source: Parcel): DidShowWebWallet = DidShowWebWallet(source)
                override fun newArray(size: Int): Array<DidShowWebWallet?> = arrayOfNulls(size)
            }
        }
    }

    class DidFinishWebWallet(): WalletSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {}

        private constructor(parcel: Parcel): this()

        override fun describeContents(): Int = 0

        companion object {
            @JvmField
            val CREATOR = object: Parcelable.Creator<DidFinishWebWallet> {
                override fun createFromParcel(source: Parcel): DidFinishWebWallet = DidFinishWebWallet(source)
                override fun newArray(size: Int): Array<DidFinishWebWallet?> = arrayOfNulls(size)
            }
        }
    }

}