package com.example.cft_converter.domain

import com.example.cft_converter.domain.entity.CurrencyBody
import io.reactivex.disposables.Disposable

interface CurrencyRepository {

    fun requestFreshListOfCurrencies(error: (Throwable) -> Unit)

    fun requestListOfCurrencies(success: (List<CurrencyBody>) -> Unit, error: (Throwable) -> Unit): Disposable
}