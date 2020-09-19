package com.example.cft_converter.data.network

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyApi {

    @GET("/daily_json.js")
    fun getListOfCurrency(): Single<JsonObject>
}