package com.example.cft_converter.di

import com.example.cft_converter.domain.RequestListCurrencyUseCase
import com.example.cft_converter.presentation.CurrencyPresenter
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideCurrencyPresenter(useCase: RequestListCurrencyUseCase): CurrencyPresenter {
        return CurrencyPresenter(useCase)
    }
}