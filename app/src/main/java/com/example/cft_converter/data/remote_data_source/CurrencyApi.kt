package com.example.cft_converter.data.remote_data_source

import com.example.cft_converter.utils.Constants.Companion.CURRENCY_LIST_API_END_POINT
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyApi {

    @GET(CURRENCY_LIST_API_END_POINT)
    fun requestListOfCurrencies(): Single<JsonObject>
}