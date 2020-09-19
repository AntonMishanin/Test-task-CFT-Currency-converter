package com.example.cft_converter.domain.callback

import com.example.cft_converter.domain.entity.CurrencyEntityDb
import io.realm.RealmResults

interface RequestFromDbCallback {

    fun onSuccess(list: RealmResults<CurrencyEntityDb>)

    fun onError(message: String)
}