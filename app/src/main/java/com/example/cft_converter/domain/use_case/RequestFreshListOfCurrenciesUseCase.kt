package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyRepository

class RequestFreshListOfCurrenciesUseCase(private val repository: CurrencyRepository) {

    operator fun invoke(error: (Throwable) -> Unit) =
        repository.requestFreshListOfCurrencies(error)
}