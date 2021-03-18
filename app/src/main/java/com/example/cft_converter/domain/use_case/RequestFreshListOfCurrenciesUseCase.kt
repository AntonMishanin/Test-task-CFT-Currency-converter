package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyRepository
import javax.inject.Inject

class RequestFreshListOfCurrenciesUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke(error: (Throwable) -> Unit) =
        repository.requestFreshListOfCurrencies(error)
}