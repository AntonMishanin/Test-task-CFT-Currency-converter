package com.example.cft_converter.data.remote_data_source

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RemoteDataSource(private val api: CurrencyApi) {

    fun requestFreshListOfCurrencies(
        success: (List<CurrencyEntity>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        api.requestListOfCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<JsonObject>() {
                override fun onSuccess(jsonObject: JsonObject) {
                    val asJson = jsonObject.getAsJsonObject("Valute")

                    val listValute: MutableList<CurrencyEntity> = ArrayList()
                    for (key in asJson.keySet()) {
                        val valute = CurrencyEntity(
                           // asJson[key].asJsonObject.getAsJsonPrimitive("ID").asString,
                           // asJson[key].asJsonObject.getAsJsonPrimitive("NumCode").asInt,
                            asJson[key].asJsonObject.getAsJsonPrimitive("CharCode").asString,
                            asJson[key].asJsonObject.getAsJsonPrimitive("Nominal").asInt,
                            asJson[key].asJsonObject.getAsJsonPrimitive("Name").asString,
                            asJson[key].asJsonObject.getAsJsonPrimitive("Value").asDouble,
                           // asJson[key].asJsonObject.getAsJsonPrimitive("Previous").asDouble
                        )
                        listValute.add(valute)
                    }

                    success(listValute)
                }

                override fun onError(e: Throwable) {
                    error(e)
                }
            })
    }
}