package com.payline.mobile.androidsdk

import android.app.Activity
import android.os.Bundle
import android.view.View

class BlankActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(View(this))
    }
}
