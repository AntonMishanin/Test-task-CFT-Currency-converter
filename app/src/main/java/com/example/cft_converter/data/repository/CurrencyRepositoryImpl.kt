package com.example.cft_converter.data.repository

import com.example.cft_converter.data.local_data_source.LocalDataSource
import com.example.cft_converter.data.remote_data_source.RemoteDataSource
import com.example.cft_converter.data.mapper.CurrencyMapper
import com.example.cft_converter.data.mapper.JsonMapper
import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class CurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : CurrencyRepository {

    override fun requestFreshListOfCurrencies(error: (Throwable) -> Unit) {
        remoteDataSource.requestFreshListOfCurrencies({ jsonObject ->
            val jsonMapper = JsonMapper()
            val listOfCurrencies = jsonMapper.invoke(jsonObject)
            localDataSource.saveListOfCurrencies(listOfCurrencies)
        }, {
            error(it)
        })
    }

    override fun requestListOfCurrencies(
        success: (List<CurrencyEntity>) -> Unit,
        error: (Throwable) -> Unit
    ): Disposable {
        return localDataSource.requestListOfCurrencies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                if (list.isEmpty()) {
                    requestFreshListOfCurrencies(error)
                } else {
                    val mapper = CurrencyMapper()
                    val currencyEntityList = mapper.mapping(list)
                    success(currencyEntityList)
                }
            }
    }
}