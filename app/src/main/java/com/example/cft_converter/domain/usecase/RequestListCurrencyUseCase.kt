package com.example.cft_converter.domain.usecase

import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyBody

class RequestListCurrencyUseCase(private val repository: ICurrencyRepository) {

    fun invoke(success: (List<CurrencyBody>) -> Unit, error: (String) -> Unit){
        repository.requestListCurrencyFromDb(success, error)
    }
}