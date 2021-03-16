package com.example.cft_converter.di

import com.example.cft_converter.domain.use_case.ConvertCurrencyUseCase
import com.example.cft_converter.domain.use_case.RequestFreshListOfCurrenciesUseCase
import com.example.cft_converter.domain.use_case.RequestListOfCurrenciesUseCase
import com.example.cft_converter.presenters.CurrencyPresenter
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideCurrencyPresenter(
        requestListOfCurrenciesUseCase: RequestListOfCurrenciesUseCase,
        convertCurrencyUseCase: ConvertCurrencyUseCase,
        requestFreshListOfCurrenciesUseCase: RequestFreshListOfCurrenciesUseCase
    ): CurrencyPresenter {
        return CurrencyPresenter(
            requestListOfCurrenciesUseCase,
            convertCurrencyUseCase,
            requestFreshListOfCurrenciesUseCase
        )
    }
}