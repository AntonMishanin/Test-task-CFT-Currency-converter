package com.example.cft_converter.domain

class CurrencyConverter {

    fun convert(
        inputValue: Double,
        inputCurrencyValue: Double,
        inputCurrencyNominal: Int,
        outputCurrencyValue: Double,
        outputCurrencyNominal: Int
    ): Double {
        val proportion =
            (outputCurrencyNominal * inputCurrencyValue) / (outputCurrencyValue * inputCurrencyNominal)
        return inputValue * proportion
    }
}