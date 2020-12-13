package com.example.cft_converter.utils

import java.lang.NumberFormatException

fun CharSequence.toValidDouble(): Double {

    if (this.toString() == "") {
        return 0.0
    }

    return try {
        this.toString().toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}