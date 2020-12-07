package com.example.cft_converter.di

import android.content.Context
import com.example.cft_converter.data.CurrencyRepositoryImpl
import com.example.cft_converter.data.database.RealmDb
import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.data.network.CurrencyRemoteDataSource
import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.RequestListCurrencyUseCase
import com.example.cft_converter.presentation.CurrencyPresenter
import com.example.cft_converter.utils.Constants.Companion.CURRENCY_API_BASE_URL
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CurrencyModule {

    @Provides
    fun provideCurrencyPresenter(useCase: RequestListCurrencyUseCase): CurrencyPresenter{
        return CurrencyPresenter(useCase)
    }

   @Provides
   fun provideRequestListCurrencyUseCase(currencyRepository: ICurrencyRepository): RequestListCurrencyUseCase {
       return RequestListCurrencyUseCase(currencyRepository)
   }

    @Provides
    fun provideCurrencyRepository(
        realmDb: RealmDb,
        remoteDataSource: CurrencyRemoteDataSource
    ): ICurrencyRepository {
        return CurrencyRepositoryImpl(realmDb, remoteDataSource)
    }

    @Provides
    fun provideRealmDb(realm: Realm): RealmDb {
        return RealmDb(realm)
    }
/*
    @Singleton
    @Provides
    fun provideRealmInstance(context: Context): Realm {
        Realm.init(context)
        val configuration: RealmConfiguration = RealmConfiguration.Builder().build()
       // Realm.setDefaultConfiguration(configuration)
        return Realm.getInstance(configuration)
    }
 */
    @Provides
    fun provideCurrencyRemoteDataSource(currencyApi: CurrencyApi): CurrencyRemoteDataSource {
        return CurrencyRemoteDataSource(currencyApi)
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
            .baseUrl(CURRENCY_API_BASE_URL)
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