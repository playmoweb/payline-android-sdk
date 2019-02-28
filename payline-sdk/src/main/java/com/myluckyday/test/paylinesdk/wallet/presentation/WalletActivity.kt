package com.myluckyday.test.paylinesdk.wallet.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.myluckyday.test.paylinesdk.R
import com.myluckyday.test.paylinesdk.app.presentation.WebViewModel

internal class WalletActivity: AppCompatActivity() {

    private val viewModel: WebViewModel by lazy {
        ViewModelProviders.of(this).get(WebViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wallet)
    }

}
