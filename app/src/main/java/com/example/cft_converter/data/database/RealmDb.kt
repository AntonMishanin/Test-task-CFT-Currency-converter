package com.example.cft_converter.data.database

import com.example.cft_converter.domain.callback.RequestFromDbCallback
import com.example.cft_converter.domain.callback.SaveToDbCallback
import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.entity.CurrencyEntityDb
import io.realm.Realm

class RealmDb {

    private val realm: Realm = Realm.getDefaultInstance()

    fun requestCurrencyEntityList(callback: RequestFromDbCallback) {
        realm.executeTransactionAsync({ realm ->
            val list = realm.where(CurrencyEntityDb::class.java).findAll()
            callback.onSuccess(list)
        }, {
        }
        ) {
            callback.onError(it.message.toString())
        }
    }

    fun saveCurrencyEntity(
        inputList: List<CurrencyBody>,
        callback: SaveToDbCallback
    ) {
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
            callback.onSuccess()
        }
        ) {
            callback.onError(it.message.toString())
        }
    }
}