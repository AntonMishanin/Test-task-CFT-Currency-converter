package com.example.cft_converter.data.network

import com.example.cft_converter.domain.callback.NetworkCallback
import com.example.cft_converter.domain.entity.CurrencyBody
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CurrencyNetwork(private val api: CurrencyApi) {

    fun requestListCurrency(callback: NetworkCallback) {
        api.getListOfCurrency()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<JsonObject>() {
                override fun onSuccess(jsonObject: JsonObject) {
                    val asJson = jsonObject.getAsJsonObject("Valute")

                    val listValute: MutableList<CurrencyBody> = ArrayList()
                    for (key in asJson.keySet()) {
                        val valute = CurrencyBody(
                            asJson[key].asJsonObject.getAsJsonPrimitive("ID").asString,
                            asJson[key].asJsonObject.getAsJsonPrimitive("NumCode").asInt,
                            asJson[key].asJsonObject.getAsJsonPrimitive("CharCode").asString,
                            asJson[key].asJsonObject.getAsJsonPrimitive("Nominal").asInt,
                            asJson[key].asJsonObject.getAsJsonPrimitive("Name").asString,
                            asJson[key].asJsonObject.getAsJsonPrimitive("Value").asDouble,
                            asJson[key].asJsonObject.getAsJsonPrimitive("Previous").asDouble
                        )
                        listValute.add(valute)
                    }

                    callback.onSuccess(listValute)
                }

                override fun onError(e: Throwable) {
                   callback.onError(e.message.toString())
                }
            })
    }
}