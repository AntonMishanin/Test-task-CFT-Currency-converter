package com.example.cft_converter.utils

import com.example.cft_converter.utils.Constants.Companion.COMMA
import com.example.cft_converter.utils.Constants.Companion.DOT

object GetDoubleFromString {

    fun invoke(inputString: String): Double? {
        val stringToDouble = try {
            val arrStrings1: List<String> = inputString.split(COMMA)
            arrStrings1[0] + DOT + arrStrings1[1]
        } catch (e: Exception) {
            inputString
        }

        return try {
            stringToDouble.toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }
}