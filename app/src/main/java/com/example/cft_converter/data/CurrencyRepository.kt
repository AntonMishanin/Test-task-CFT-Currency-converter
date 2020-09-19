package com.example.cft_converter.data

import android.util.Log
import com.example.cft_converter.data.database.RealmDb
import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.data.network.CurrencyRequest
import com.example.cft_converter.domain.CurrencyMapper
import com.example.cft_converter.domain.callback.RequestFromDbCallback
import com.example.cft_converter.domain.callback.SaveToDbCallback
import com.example.cft_converter.domain.callback.NetworkCallback
import com.example.cft_converter.domain.callback.PresentationCallback
import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.entity.CurrencyEntityDb
import io.realm.RealmResults

class CurrencyRepository {

    private val request = CurrencyRequest()
    private val realmDb = RealmDb()
    private lateinit var api: CurrencyApi

    fun requestListCurrencyFromNetwork(api: CurrencyApi, callback: PresentationCallback) {
        this.api = api
        request.requestListCurrency(api, object : NetworkCallback {
            override fun onSuccess(listValute: List<CurrencyBody>) {
                Log.d("TAG", "requestListCurrencyFromNetwork success")
                saveCurrencyToDb(
                    listValute,
                    callback
                )
            }

            override fun onError(message: String) {
                Log.d("TAG", "requestListCurrencyFromNetwork error")
                callback.onError(message)
            }
        })
    }

    fun requestListCurrencyFromDb(api: CurrencyApi, callback: PresentationCallback) {
        this.api = api
        realmDb.requestCurrencyEntityList(object : RequestFromDbCallback {
            override fun onSuccess(list: RealmResults<CurrencyEntityDb>) {
                Log.d("TAG", "requestListCurrencyFromDb ${list.size}")
                if (list.isEmpty()) {
                    requestListCurrencyFromNetwork(api, callback)
                } else {
                    val mapper = CurrencyMapper()
                    val currencyEntityList = mapper.mapping(list)
                    callback.onSuccess(currencyEntityList)
                }
            }

            override fun onError(message: String) {
                Log.d("TAG", "requestListCurrencyFromDb $message")
                callback.onError(message)
            }
        })
    }

    fun saveCurrencyToDb(
        inputList: List<CurrencyBody>,
        callback: PresentationCallback
    ) {
        realmDb.saveCurrencyEntity(inputList, object : SaveToDbCallback {
            override fun onSuccess() {
                Log.d("TAG", "saveCurrencyToDb onSuccess()")
                requestListCurrencyFromDb(api, callback)
            }

            override fun onError(message: String) {
                Log.d("TAG", "saveCurrencyToDb $message")
                callback.onError(message)
            }
        })
    }
}