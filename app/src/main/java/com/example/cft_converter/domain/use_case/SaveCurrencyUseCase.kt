package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class SaveCurrencyUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke(jsonObject: JsonObject) = repository.saveCurrency(jsonObject)
}