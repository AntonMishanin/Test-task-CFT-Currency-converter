package com.example.cft_converter.domain.callback

import com.example.cft_converter.domain.entity.CurrencyBody

interface NetworkCallback {

    fun onSuccess(listValute: List<CurrencyBody>)

    fun onError(message: String)
}