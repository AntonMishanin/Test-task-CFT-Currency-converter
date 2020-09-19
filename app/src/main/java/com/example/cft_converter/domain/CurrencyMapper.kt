package com.example.cft_converter.domain

import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.entity.CurrencyEntityDb
import io.realm.RealmResults

class CurrencyMapper {

    fun mapping(inputList: RealmResults<CurrencyEntityDb>): List<CurrencyBody> {

        val outputList: MutableList<CurrencyBody> = ArrayList()
        for (i in inputList.indices) {
            val currency = CurrencyBody()
            currency.Nominal = inputList[i]?.nominal ?: 0
            currency.Value = inputList[i]?.value ?: 0.0
            currency.Name = inputList[i]?.name ?: ""
            currency.CharCode = inputList[i]?.charCode ?: ""

            outputList.add(currency)
        }
        return outputList
    }
}