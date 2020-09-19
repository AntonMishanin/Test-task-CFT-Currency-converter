package com.example.cft_converter.domain

import com.example.cft_converter.data.CurrencyRepository
import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.domain.callback.NetworkCallback

class CurrencyUseCase {

   private val repository = CurrencyRepository()

    fun requestListCurrency(api: CurrencyApi, callback: NetworkCallback) {
        repository.requestListCurrency(api, callback)
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
}