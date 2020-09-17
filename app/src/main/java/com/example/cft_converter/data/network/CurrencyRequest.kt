package com.example.cft_converter.data.network

import com.example.cft_converter.domain.callback.NetworkCallback
import com.example.cft_converter.domain.entity.CurrencyEntityNetwork


class CurrencyRequest {

    fun requestListCurrency(api: CurrencyApi, callback: NetworkCallback){
        api.getListOfCurrency()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<CurrencyEntityNetwork>>() {
                fun onSuccess(list: List<CurrencyEntityNetwork>) {
                    callback.onSuccess(list)
                }

                fun onError(e: Throwable) {
                    callback.onError(e.message)
                }
            })
    }
}