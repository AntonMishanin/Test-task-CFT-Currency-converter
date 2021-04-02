package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyRepository
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class RequestFreshListOfCurrenciesUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke(): Single<JsonObject> = repository.requestFreshListOfCurrencies()
}