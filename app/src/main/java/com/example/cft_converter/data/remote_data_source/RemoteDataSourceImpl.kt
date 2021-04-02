package com.example.cft_converter.data.remote_data_source

import com.example.cft_converter.data.repository.RemoteDataSource

class RemoteDataSourceImpl(private val api: CurrencyApi) : RemoteDataSource {

    override fun requestFreshListOfCurrencies() = api.requestListOfCurrencies()
}