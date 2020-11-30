package com.example.cft_converter.domain

import com.example.cft_converter.domain.callback.PresentationCallback

class RequestListCurrencyUseCase(private val repository: ICurrencyRepository) {

    fun fromDb(callback: PresentationCallback){
        repository.requestListCurrencyFromDb(callback)
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
        callback: PresentationCallback
    ) {
        repository.requestListCurrencyFromNetwork(callback)
    }
}