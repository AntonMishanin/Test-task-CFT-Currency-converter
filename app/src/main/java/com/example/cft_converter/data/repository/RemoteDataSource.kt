package com.example.cft_converter.data.repository

import com.google.gson.JsonObject

interface RemoteDataSource {

    fun requestFreshListOfCurrencies(
        success: (JsonObject) -> Unit,
        error: (Throwable) -> Unit
    )
}