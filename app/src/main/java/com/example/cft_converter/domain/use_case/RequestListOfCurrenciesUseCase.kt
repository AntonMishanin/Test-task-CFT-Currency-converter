package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyBody

class RequestListOfCurrenciesUseCase(private val repository: CurrencyRepository) {

    operator fun invoke(success: (List<CurrencyBody>) -> Unit, error: (Throwable) -> Unit) {
        repository.requestListOfCurrencies(success, error)
    }
}