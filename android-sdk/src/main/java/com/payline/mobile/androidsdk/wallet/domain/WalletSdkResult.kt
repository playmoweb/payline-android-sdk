package com.payline.mobile.androidsdk.wallet.domain

import android.os.Parcel
import android.os.Parcelable
import com.payline.mobile.androidsdk.core.domain.SdkResult

internal sealed class WalletSdkResult: SdkResult {

    class DidShowWebWallet(): WalletSdkResult() {

        override fun writeToParcel(dest: Parcel, flags: Int) {
            //The function doesn't have parameters
        }

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

}
