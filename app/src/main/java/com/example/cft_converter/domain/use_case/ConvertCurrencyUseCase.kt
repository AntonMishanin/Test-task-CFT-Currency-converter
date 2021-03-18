package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyConverter
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(private val converter: CurrencyConverter) {

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