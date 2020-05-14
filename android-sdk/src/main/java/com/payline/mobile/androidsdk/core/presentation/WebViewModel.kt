package com.payline.mobile.androidsdk.core.presentation

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class WebViewModel: ViewModel() {

    val isLoading = MutableLiveData<Boolean>().apply {
        value = true
    }

    val finishUi = MutableLiveData<Boolean>().apply {
        value = false
    }

    val hideCancelButton = MutableLiveData<Boolean>().apply {
        value = false
    }

    var currentUrl: Uri? = null

}
