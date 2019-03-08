package com.payline.mobile.androidsdk

import com.payline.mobile.androidsdk.wallet.WalletControllerListener

class TestWalletListener: WalletControllerListener {

    var didShowManageWebWallet = false

    override fun didShowManageWebWallet() {
        didShowManageWebWallet = true
    }

}