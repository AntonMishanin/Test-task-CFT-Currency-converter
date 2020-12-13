package com.example.cft_converter.di

import com.example.cft_converter.data.CurrencyRepositoryImpl
import com.example.cft_converter.data.database.RealmDb
import com.example.cft_converter.data.network.CurrencyRemoteDataSource
import com.example.cft_converter.domain.ICurrencyRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideCurrencyRepository(
        realmDb: RealmDb,
        remoteDataSource: CurrencyRemoteDataSource
    ): ICurrencyRepository {
        return CurrencyRepositoryImpl(realmDb, remoteDataSource)
    }
}