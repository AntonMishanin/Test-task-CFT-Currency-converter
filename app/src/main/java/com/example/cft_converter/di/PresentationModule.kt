package com.example.cft_converter.di

import com.example.cft_converter.domain.usecase.ConvertCurrencyUseCase
import com.example.cft_converter.domain.usecase.ReloadListCurrencyUseCase
import com.example.cft_converter.domain.usecase.RequestListCurrencyUseCase
import com.example.cft_converter.presentation.CurrencyPresenter
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideCurrencyPresenter(
        requestListCurrencyUseCase: RequestListCurrencyUseCase,
        convertCurrencyUseCase: ConvertCurrencyUseCase,
        reloadListCurrencyUseCase: ReloadListCurrencyUseCase
    ): CurrencyPresenter {
        return CurrencyPresenter(
            requestListCurrencyUseCase,
            convertCurrencyUseCase,
            reloadListCurrencyUseCase
        )
    }
}