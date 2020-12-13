package com.example.cft_converter.di

import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.RequestListCurrencyUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideRequestListCurrencyUseCase(currencyRepository: ICurrencyRepository): RequestListCurrencyUseCase {
        return RequestListCurrencyUseCase(currencyRepository)
    }
}