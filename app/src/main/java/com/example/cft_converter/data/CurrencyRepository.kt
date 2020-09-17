package com.example.cft_converter.data

import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.data.network.CurrencyRequest
import com.example.cft_converter.domain.callback.NetworkCallback

class CurrencyRepository {

    val request = CurrencyRequest()

    fun requestListCurrency(api: CurrencyApi, callback: NetworkCallback){
        request.requestListCurrency(api, callback)
    }
}