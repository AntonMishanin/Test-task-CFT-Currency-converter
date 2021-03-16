package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyConverter

class ConvertCurrencyUseCase(private val converter: CurrencyConverter) {

    operator fun invoke(
        inputValue: Double,
        inputCurrencyValue: Double,
        inputNominal: Int,
        outputCurrencyValue: Double,
        outputNominal: Int
    ): Double {
        return converter.convert(
            inputValue,
            inputCurrencyValue,
            inputNominal,
            outputCurrencyValue,
            outputNominal
        )
    }
}