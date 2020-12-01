package com.example.cft_converter.domain

import com.example.cft_converter.domain.entity.CurrencyBody

interface ICurrencyRepository {

    fun requestListCurrencyFromNetwork(
        success: (List<CurrencyBody>) -> Unit,
        error: (String) -> Unit
    )

    fun requestListCurrencyFromDb(success: (List<CurrencyBody>) -> Unit, error: (String) -> Unit)
}