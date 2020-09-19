package com.example.cft_converter.data.network

import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitService {

    private fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    private fun provideRxJava2CallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru")
            .addCallAdapterFactory(provideRxJava2CallAdapterFactory())
            .addConverterFactory(provideGsonConverterFactory())
            .build()
    }

    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }
}