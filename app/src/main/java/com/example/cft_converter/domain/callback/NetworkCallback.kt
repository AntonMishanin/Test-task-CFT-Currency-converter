package com.example.cft_converter.domain.callback

import com.example.cft_converter.domain.entity.CurrencyEntityNetwork

interface NetworkCallback {

    fun onSuccess(list: List<CurrencyEntityNetwork>)

    fun onError(message: String?)
}