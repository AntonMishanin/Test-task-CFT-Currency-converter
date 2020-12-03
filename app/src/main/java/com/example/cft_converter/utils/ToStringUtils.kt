package com.example.cft_converter.utils

fun Double.toStringWithDot():String{
    val valueWithComma = String.format("%.3f", this)
    val arrStrings: List<String> = valueWithComma.split(Constants.COMMA)
    return arrStrings[0] + Constants.DOT + arrStrings[1]
}