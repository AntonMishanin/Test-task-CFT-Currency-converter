package com.example.cft_converter.di

import com.example.cft_converter.domain.CurrencyConverter
import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.use_case.ConvertCurrencyUseCase
import com.example.cft_converter.domain.use_case.RequestFreshListOfCurrenciesUseCase
import com.example.cft_converter.domain.use_case.RequestListOfCurrenciesUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideRequestListCurrencyUseCase(currencyRepository: CurrencyRepository): RequestListOfCurrenciesUseCase {
        return RequestListOfCurrenciesUseCase(currencyRepository)
    }

    @Provides
    fun provideReloadListCurrencyUseCase(currencyRepository: CurrencyRepository): RequestFreshListOfCurrenciesUseCase {
        return RequestFreshListOfCurrenciesUseCase(currencyRepository)
    }

    @Provides
    fun provideConvertCurrencyUseCase(converter: CurrencyConverter): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCase(converter)
    }

    @Provides
    fun provideCurrencyConverter(): CurrencyConverter {
        return CurrencyConverter()
    }
}