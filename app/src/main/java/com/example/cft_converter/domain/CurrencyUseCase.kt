package com.example.cft_converter.domain

import com.example.cft_converter.data.CurrencyRepository
import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.domain.callback.NetworkCallback
import com.example.cft_converter.domain.callback.PresentationCallback

class CurrencyUseCase {

   private val repository = CurrencyRepository()

    fun requestListCurrencyFromDb(api: CurrencyApi, callback: PresentationCallback){
        repository.requestListCurrencyFromDb(api, callback)
    }

    fun requestListCurrency(api: CurrencyApi, callback: PresentationCallback) {
        repository.requestListCurrencyFromNetwork(api, callback)
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

    fun onReloadCurrencyList(
        api: CurrencyApi,
        callback: PresentationCallback
    ) {
        repository.requestListCurrencyFromNetwork(api, callback)
    }
}