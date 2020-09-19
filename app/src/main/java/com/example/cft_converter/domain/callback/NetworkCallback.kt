package com.example.cft_converter.domain.callback

import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.entity.CurrencyEntityNetwork

interface NetworkCallback {

    fun onSuccess(listValute: List<CurrencyBody>)

    fun onError(message: String?)
}