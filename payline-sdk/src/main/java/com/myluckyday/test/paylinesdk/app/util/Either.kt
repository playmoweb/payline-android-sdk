package com.myluckyday.test.paylinesdk.app.util

data class Either<One,Other>(
    val one: One?,
    val other: Other?
)