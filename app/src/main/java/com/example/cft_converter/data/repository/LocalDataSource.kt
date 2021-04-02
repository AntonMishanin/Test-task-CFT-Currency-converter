package com.example.cft_converter.data.repository

import com.example.cft_converter.data.dto.LocalCurrencyDto
import com.example.cft_converter.domain.entity.CurrencyEntity
import io.reactivex.Flowable
import io.realm.RealmResults

interface LocalDataSource {

    fun requestListOfCurrencies(): Flowable<RealmResults<LocalCurrencyDto>>

    fun saveListOfCurrencies(listOfCurrency: List<CurrencyEntity>)

    fun deleteAllCurrencies(onSuccess: () -> Unit)
}