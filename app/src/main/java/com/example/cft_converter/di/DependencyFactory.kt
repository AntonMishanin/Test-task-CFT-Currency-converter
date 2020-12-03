package com.example.cft_converter.di

import com.example.cft_converter.data.CurrencyRepositoryImpl
import com.example.cft_converter.data.database.RealmDb
import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.data.network.CurrencyRemoteDataSource
import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.RequestListCurrencyUseCase
import com.example.cft_converter.presentation.CurrencyPresenter
import io.realm.Realm
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DependencyFactory {

    @Volatile
    var retrofit: Retrofit? = null

    fun provideCurrencyPresenter(): CurrencyPresenter {
        return CurrencyPresenter(provideRequestListCurrencyUseCase())
    }

    private fun provideRequestListCurrencyUseCase(): RequestListCurrencyUseCase {
        return RequestListCurrencyUseCase(provideCurrencyRepository())
    }

    private fun provideCurrencyRepository(): ICurrencyRepository {
        return CurrencyRepositoryImpl(provideRealmDb(), provideCurrencyRemoteDataSource())
    }

    private fun provideRealmDb(): RealmDb {
        return RealmDb(provideRealmInstance())
    }

    private fun provideRealmInstance(): Realm {
        return Realm.getDefaultInstance()
    }

    private fun provideCurrencyRemoteDataSource(): CurrencyRemoteDataSource {
        return CurrencyRemoteDataSource(provideCurrencyApi())
    }

    private fun provideCurrencyApi(): CurrencyApi {
        return provideRetrofit().create(CurrencyApi::class.java)
    }

    private fun provideRetrofit(): Retrofit {
        if (retrofit == null) {
            synchronized(this) {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl("https://www.cbr-xml-daily.ru")
                        .addCallAdapterFactory(provideRxJava2CallAdapterFactory())
                        .addConverterFactory(provideGsonConverterFactory())
                        .build()
                }
            }
        }
        return retrofit as Retrofit
    }

    private fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    private fun provideRxJava2CallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }
}