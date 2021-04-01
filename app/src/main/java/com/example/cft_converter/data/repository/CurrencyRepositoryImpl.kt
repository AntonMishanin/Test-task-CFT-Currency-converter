package com.example.cft_converter.data.repository

import com.example.cft_converter.data.mapper.CurrencyMapper
import com.example.cft_converter.data.mapper.JsonMapper
import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class CurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val currencyMapper: CurrencyMapper,
    private val jsonMapper: JsonMapper
) : CurrencyRepository {

    override fun requestFreshListOfCurrencies(error: (Throwable) -> Unit) =
        remoteDataSource.requestFreshListOfCurrencies({ jsonObject ->
            val listOfCurrencies = jsonMapper.invoke(jsonObject)
            localDataSource.saveListOfCurrencies(listOfCurrencies)
        }, {
            error(it)
        })

    override fun requestListOfCurrencies(
        success: (List<CurrencyEntity>) -> Unit,
        error: (Throwable) -> Unit
    ): Disposable = localDataSource.requestListOfCurrencies()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { listOfCurrencies ->
            if (listOfCurrencies.isEmpty()) {
                requestFreshListOfCurrencies { error(it) }
            } else {
                val currencyEntityList = currencyMapper.mapping(listOfCurrencies)
                success(currencyEntityList)
            }
        }
}