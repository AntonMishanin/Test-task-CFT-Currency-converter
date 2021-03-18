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

fun Double.toStringWithDot(): String {
    val valueWithComma = String.format("%.3f", this)
    val arrStrings: List<String> = valueWithComma.split(Constants.COMMA)
    return try {
        arrStrings[0] + Constants.DOT + arrStrings[1]
    } catch (e: IndexOutOfBoundsException) {
        "0.0"
    }
}