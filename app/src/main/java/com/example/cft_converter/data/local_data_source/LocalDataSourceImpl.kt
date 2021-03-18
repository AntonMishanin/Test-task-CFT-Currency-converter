package com.example.cft_converter.data.local_data_source

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.data.dto.LocalCurrencyDto
import com.example.cft_converter.data.repository.LocalDataSource
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults

class LocalDataSourceImpl(private val realm: Realm) : LocalDataSource {

    override fun requestListOfCurrencies(): Flowable<RealmResults<LocalCurrencyDto>> =
        realm.where(LocalCurrencyDto::class.java)
            .findAllAsync()
            .asFlowable()

    override fun saveListOfCurrencies(listOfCurrency: List<CurrencyEntity>) {
        realm.executeTransactionAsync({ realm ->
            realm.deleteAll()

            for (i in listOfCurrency.indices) {
                val currency = realm.createObject(LocalCurrencyDto::class.java)
                currency.charCode = listOfCurrency[i].charCode
                currency.name = listOfCurrency[i].name
                currency.nominal = listOfCurrency[i].nominal
                currency.value = listOfCurrency[i].value
            }
        }, {
        }
        ) {
            it.printStackTrace()
        }
    }
}