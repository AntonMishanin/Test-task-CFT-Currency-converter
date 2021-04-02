package com.example.cft_converter.domain

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface CurrencyRepository {

    fun requestFreshListOfCurrencies(): Single<JsonObject>

    fun requestListOfCurrencies(success: (List<CurrencyEntity>) -> Unit, error: (Throwable) -> Unit): Disposable

    fun saveCurrency(jsonObject: JsonObject)
}