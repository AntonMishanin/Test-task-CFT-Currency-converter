package com.example.cft_converter.data

import com.example.cft_converter.data.database.RealmDb
import com.example.cft_converter.data.network.CurrencyRemoteDataSource
import com.example.cft_converter.domain.CurrencyMapper
import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.callback.RequestFromDbCallback
import com.example.cft_converter.domain.callback.SaveToDbCallback
import com.example.cft_converter.domain.callback.NetworkCallback
import com.example.cft_converter.domain.callback.PresentationCallback
import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.entity.CurrencyEntityDb
import io.realm.RealmResults

class CurrencyRepositoryImpl(
    private val realmDb: RealmDb,
    private val request: CurrencyRemoteDataSource
) : ICurrencyRepository {

    override fun requestListCurrencyFromNetwork(callback: PresentationCallback) {
        request.requestListCurrency(object : NetworkCallback {
            override fun onSuccess(listValute: List<CurrencyBody>) {
                saveCurrencyToDb(
                    listValute,
                    callback
                )
            }

            override fun onError(message: String) {
                callback.onError(message)
            }
        })
    }

    override fun requestListCurrencyFromDb(callback: PresentationCallback) {
        realmDb.requestCurrencyEntityList(object : RequestFromDbCallback {
            override fun onSuccess(list: RealmResults<CurrencyEntityDb>) {
                if (list.isEmpty()) {
                    requestListCurrencyFromNetwork(callback)
                } else {
                    val mapper = CurrencyMapper()
                    val currencyEntityList = mapper.mapping(list)
                    callback.onSuccess(currencyEntityList)
                }
            }

            override fun onError(message: String) {
                callback.onError(message)
            }
        })
    }

    private fun saveCurrencyToDb(
        inputList: List<CurrencyBody>,
        callback: PresentationCallback
    ) {
        realmDb.saveCurrencyEntity(inputList, object : SaveToDbCallback {
            override fun onSuccess() {
                requestListCurrencyFromDb(callback)
            }

            override fun onError(message: String) {
                callback.onError(message)
            }
        })
    }
}