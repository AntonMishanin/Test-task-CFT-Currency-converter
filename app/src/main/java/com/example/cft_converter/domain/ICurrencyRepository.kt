package com.example.cft_converter.domain

import com.example.cft_converter.domain.callback.PresentationCallback

interface ICurrencyRepository {

    fun requestListCurrencyFromNetwork(callback: PresentationCallback)

    fun requestListCurrencyFromDb(callback: PresentationCallback)
}