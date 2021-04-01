package com.example.cft_converter.data.remote_data_source

import com.example.cft_converter.data.repository.RemoteDataSource
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RemoteDataSourceImpl(private val api: CurrencyApi) : RemoteDataSource {

    override fun requestFreshListOfCurrencies(
        success: (JsonObject) -> Unit,
        error: (Throwable) -> Unit
    ) = api.requestListOfCurrencies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : DisposableSingleObserver<JsonObject>() {
            override fun onSuccess(jsonObject: JsonObject) {
                success(jsonObject)
            }

            override fun onError(e: Throwable) {
                error(e)
            }
        })
}