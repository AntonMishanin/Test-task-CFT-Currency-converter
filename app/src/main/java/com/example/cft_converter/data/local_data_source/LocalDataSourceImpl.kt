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

    override fun saveListOfCurrencies(inputList: List<CurrencyEntity>) {
        realm.executeTransactionAsync({ realm ->
            realm.deleteAll()

            for (i in inputList.indices) {
                val currency = realm.createObject(LocalCurrencyDto::class.java)
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