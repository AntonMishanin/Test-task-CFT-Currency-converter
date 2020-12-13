package com.example.cft_converter.domain

import com.example.cft_converter.domain.entity.CurrencyBody

class RequestListCurrencyUseCase(private val repository: ICurrencyRepository) {

    fun fromDb(success: (List<CurrencyBody>) -> Unit, error: (String) -> Unit){
        repository.requestListCurrencyFromDb(success, error)
    }

    fun convertCurrency(
        inputValue: Double,
        inputCurrencyValue: Double,
        inputNominal: Int,
        outputCurrencyValue: Double,
        outputNominal: Int
    ): Double {
        val converter = CurrencyConverter()
        return converter.convert(
            inputValue,
            inputCurrencyValue,
            inputNominal,
            outputCurrencyValue,
            outputNominal
        )
    }

    fun reload(
        success: (List<CurrencyBody>) -> Unit,
        error: (String) -> Unit
    ) {
        repository.requestListCurrencyFromNetwork(success, error)
    }
}