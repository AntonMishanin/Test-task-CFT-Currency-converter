package com.example.cft_converter.domain.callback

import com.example.cft_converter.domain.entity.CurrencyBody

interface PresentationCallback {

    fun onSuccess(listValute: List<CurrencyBody>)

    fun onError(message: String)
}