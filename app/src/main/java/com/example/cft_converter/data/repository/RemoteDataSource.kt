package com.example.cft_converter.data.repository

import com.google.gson.JsonObject
import io.reactivex.Single

interface RemoteDataSource {

    fun requestFreshListOfCurrencies(): Single<JsonObject>
}