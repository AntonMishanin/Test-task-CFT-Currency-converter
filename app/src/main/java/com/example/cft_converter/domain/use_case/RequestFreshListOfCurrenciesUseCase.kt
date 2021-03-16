package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyBody

class RequestFreshListOfCurrenciesUseCase(private val repository: CurrencyRepository) {

    operator fun invoke(error: (Throwable) -> Unit) =
        repository.requestFreshListOfCurrencies(error)
}