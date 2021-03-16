package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyEntity
import io.reactivex.disposables.Disposable

class RequestListOfCurrenciesUseCase(private val repository: CurrencyRepository) {

    operator fun invoke(
        success: (List<CurrencyEntity>) -> Unit,
        error: (Throwable) -> Unit
    ): Disposable {
        return repository.requestListOfCurrencies(success, error)
    }
}