package com.example.cft_converter.data.repository

import com.example.cft_converter.data.mapper.CurrencyMapper
import com.example.cft_converter.data.mapper.JsonMapper
import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyEntity
import com.google.gson.JsonObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class CurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val currencyMapper: CurrencyMapper,
    private val jsonMapper: JsonMapper
) : CurrencyRepository {

    override fun requestFreshListOfCurrencies(): Single<JsonObject> =
        remoteDataSource.requestFreshListOfCurrencies()

    override fun saveCurrency(jsonObject: JsonObject) {
        localDataSource.deleteAllCurrencies{
            val listOfCurrencies = jsonMapper.invoke(jsonObject)
            localDataSource.saveListOfCurrencies(listOfCurrencies)
        }
    }

    override fun requestListOfCurrencies(
        success: (List<CurrencyEntity>) -> Unit,
        error: (Throwable) -> Unit
    ): Disposable = localDataSource.requestListOfCurrencies()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { listOfCurrenciesDb ->
            val listOfCurrenciesUi = currencyMapper.mapping(listOfCurrenciesDb)
            success(listOfCurrenciesUi)
        }
}