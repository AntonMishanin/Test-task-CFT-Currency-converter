package com.example.cft_converter.di

import com.example.cft_converter.BuildConfig
import com.example.cft_converter.data.remote_data_source.CurrencyApi
import com.example.cft_converter.data.remote_data_source.RemoteDataSourceImpl
import com.example.cft_converter.data.repository.RemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteDataSourceModule {

    @Provides
    fun provideCurrencyRemoteDataSource(currencyApi: CurrencyApi): RemoteDataSource =
        RemoteDataSourceImpl(currencyApi)

    @Provides
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi =
        retrofit.create(CurrencyApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        rxJavaAdapterFactory: CallAdapter.Factory,
        gsonConverterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addCallAdapterFactory(rxJavaAdapterFactory)
        .addConverterFactory(gsonConverterFactory)
        .build()

    @Provides
    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    fun provideRxJava2CallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()
}