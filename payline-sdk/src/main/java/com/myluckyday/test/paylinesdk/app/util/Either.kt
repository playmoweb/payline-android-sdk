package com.myluckyday.test.paylinesdk.app.util

import android.os.Parcelable

internal interface Either<One,Other> {
    val one: One?
    val other: Other?
}

internal interface ParcelableEither<One,Other>: Either<One, Other>,
    Parcelable