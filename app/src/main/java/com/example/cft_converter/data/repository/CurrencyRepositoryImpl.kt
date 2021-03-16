package com.example.cft_converter.data.repository

import com.example.cft_converter.data.local_data_source.LocalDataSource
import com.example.cft_converter.data.remote_data_source.RemoteDataSource
import com.example.cft_converter.data.mapper.CurrencyMapper
import com.example.cft_converter.domain.CurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyBody
import io.reactivex.android.schedulers.AndroidSchedulers


class CurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : CurrencyRepository {

    override fun requestFreshListOfCurrencies(error: (Throwable) -> Unit) {
        remoteDataSource.requestFreshListOfCurrencies({listOfCurrencies->
            localDataSource.saveListOfCurrencies(listOfCurrencies)
        }, {
            error(it)
        })
    }

    override fun requestListOfCurrencies(
        success: (List<CurrencyBody>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        localDataSource.requestListOfCurrencies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                if (list.isEmpty()) {
                    requestFreshListOfCurrencies(error)
                } else {
                    val mapper =
                        CurrencyMapper()
                    val currencyEntityList = mapper.mapping(list)
                    success(currencyEntityList)
                }
            }
    }
}