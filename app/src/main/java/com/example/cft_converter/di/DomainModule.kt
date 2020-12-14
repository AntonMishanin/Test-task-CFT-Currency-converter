package com.example.cft_converter.di

import com.example.cft_converter.domain.CurrencyConverter
import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.usecase.ConvertCurrencyUseCase
import com.example.cft_converter.domain.usecase.ReloadListCurrencyUseCase
import com.example.cft_converter.domain.usecase.RequestListCurrencyUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideRequestListCurrencyUseCase(currencyRepository: ICurrencyRepository): RequestListCurrencyUseCase {
        return RequestListCurrencyUseCase(currencyRepository)
    }

    @Provides
    fun provideReloadListCurrencyUseCase(currencyRepository: ICurrencyRepository): ReloadListCurrencyUseCase {
        return ReloadListCurrencyUseCase(currencyRepository)
    }

    @Provides
    fun provideConvertCurrencyUseCase(
        currencyRepository: ICurrencyRepository,
        converter: CurrencyConverter
    ): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(currencyRepository, converter)
    }

    @Provides
    fun provideCurrencyConverter(): CurrencyConverter {
        return CurrencyConverter()
    }
}