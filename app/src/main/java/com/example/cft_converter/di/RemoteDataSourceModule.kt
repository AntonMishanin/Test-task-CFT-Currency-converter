package com.example.cft_converter.di

import com.example.cft_converter.data.remote_data_source.CurrencyApi
import com.example.cft_converter.data.remote_data_source.RemoteDataSource
import com.example.cft_converter.utils.Constants
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
    fun provideCurrencyRemoteDataSource(currencyApi: CurrencyApi): RemoteDataSource {
        return RemoteDataSource(currencyApi)
    }

    @Provides
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        rxJavaAdapterFactory: CallAdapter.Factory,
        gsonConverterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.CURRENCY_API_BASE_URL)
            .addCallAdapterFactory(rxJavaAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRxJava2CallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }
}