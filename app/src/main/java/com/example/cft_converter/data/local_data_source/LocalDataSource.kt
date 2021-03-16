package com.example.cft_converter.data.local_data_source

import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.entity.CurrencyEntityDb
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults

class LocalDataSource(private val realm: Realm) {

    fun requestListOfCurrencies(): Flowable<RealmResults<CurrencyEntityDb>> {
        val query = realm.where(CurrencyEntityDb::class.java)

        return query
            .findAllAsync()
            .asFlowable()
    }

    fun saveListOfCurrencies(inputList: List<CurrencyBody>) {
        realm.executeTransactionAsync({ realm ->
            realm.deleteAll()

            for (i in inputList.indices) {
                val currency = realm.createObject(CurrencyEntityDb::class.java)
                currency.charCode = inputList[i].CharCode
                currency.name = inputList[i].Name
                currency.nominal = inputList[i].Nominal
                currency.value = inputList[i].Value
            }
        }, {
        }
        ) {
            it.printStackTrace()
        }
    }
}