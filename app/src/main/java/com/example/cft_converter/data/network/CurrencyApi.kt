package com.example.cft_converter.data.network

import com.example.cft_converter.domain.entity.CurrencyEntityNetwork

interface CurrencyApi {

    @GET("sequeniatesttask/films.json")
    fun getListOfCurrency():  Single<CurrencyEntityNetwork>
}