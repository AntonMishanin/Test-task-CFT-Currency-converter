package com.example.cft_converter.domain

import com.example.cft_converter.data.CurrencyRepository
import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.domain.callback.NetworkCallback

class CurrencyUseCase {

    val repository = CurrencyRepository()

    fun requestListCurrency(api: CurrencyApi, callback: NetworkCallback){
        repository.requestListCurrency(api, callback)
    }
}