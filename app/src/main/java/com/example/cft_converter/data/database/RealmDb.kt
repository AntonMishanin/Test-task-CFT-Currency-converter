package com.example.cft_converter.data.database

import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.entity.CurrencyEntityDb
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults

class RealmDb(private val realm: Realm) {

    fun requestCurrencyEntityList(): Flowable<RealmResults<CurrencyEntityDb>> {
        val query = realm.where(CurrencyEntityDb::class.java)
        val flowable: Flowable<RealmResults<CurrencyEntityDb>>

        flowable = query
            .findAllAsync()
            .asFlowable()

        return flowable
    }

    fun saveCurrencyEntity(
        inputList: List<CurrencyBody>,
        success: () -> Unit,
        error: (String) -> Unit
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
            success()
        }
        ) {
            error(it.message.toString())
        }
    }
}