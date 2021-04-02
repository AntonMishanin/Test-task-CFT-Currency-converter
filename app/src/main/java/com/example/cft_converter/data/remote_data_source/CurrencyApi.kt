package com.example.cft_converter.data.remote_data_source

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyApi {

    @GET("/daily_json.js")
    fun requestListOfCurrencies(): Single<JsonObject>
}