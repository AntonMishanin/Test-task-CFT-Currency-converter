package com.example.cft_converter.di

import com.example.cft_converter.data.repository.CurrencyRepositoryImpl
import com.example.cft_converter.data.local_data_source.LocalDataSource
import com.example.cft_converter.data.remote_data_source.RemoteDataSource
import com.example.cft_converter.domain.CurrencyRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCurrencyRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(localDataSource, remoteDataSource)
    }
}