package com.example.cft_converter.presentation

import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.domain.CurrencyUseCase
import com.example.cft_converter.domain.callback.NetworkCallback

class CurrencyPresenter(
    private val view: CurrencyView,
    private val api: CurrencyApi,
    private val useCase: CurrencyUseCase
) {

    fun onViewCreated() {

    }

    fun requestListCurrency(api: CurrencyApi, callback: NetworkCallback) {
        useCase.requestListCurrency(api, callback)
    }
}