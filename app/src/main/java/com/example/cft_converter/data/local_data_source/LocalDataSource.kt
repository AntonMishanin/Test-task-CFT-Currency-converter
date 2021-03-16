package com.example.cft_converter.data.local_data_source

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.domain.entity.LocalCurrencyEntity
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults

class LocalDataSource(private val realm: Realm) {

    fun requestListOfCurrencies(): Flowable<RealmResults<LocalCurrencyEntity>> =
        realm.where(LocalCurrencyEntity::class.java)
            .findAllAsync()
            .asFlowable()

    fun saveListOfCurrencies(inputList: List<CurrencyEntity>) {
        realm.executeTransactionAsync({ realm ->
            realm.deleteAll()

            for (i in inputList.indices) {
                val currency = realm.createObject(LocalCurrencyEntity::class.java)
                currency.charCode = inputList[i].charCode
                currency.name = inputList[i].name
                currency.nominal = inputList[i].nominal
                currency.value = inputList[i].value
            }
        }, {
        }
        ) {
            it.printStackTrace()
        }
    }
}