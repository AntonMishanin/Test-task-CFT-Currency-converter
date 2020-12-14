package com.example.cft_converter.domain.usecase

import com.example.cft_converter.domain.CurrencyConverter
import com.example.cft_converter.domain.ICurrencyRepository

class ConvertCurrencyUseCase(private val repository: ICurrencyRepository, private val converter: CurrencyConverter) {

    fun invoke(
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