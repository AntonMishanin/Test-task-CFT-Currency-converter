package com.example.cft_converter.domain.use_case

import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyEntity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RequestListOfCurrenciesUseCase @Inject constructor(private val repository: CurrencyRepository) {

    operator fun invoke(
        success: (List<CurrencyEntity>) -> Unit,
        error: (Throwable) -> Unit
    ): Disposable {
        return repository.requestListOfCurrencies(success, error)
    }
}