package com.example.cft_converter.data.mapper

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.data.dto.LocalCurrencyDto
import io.realm.RealmResults

class CurrencyMapper {

    fun mapping(inputList: RealmResults<LocalCurrencyDto>): List<CurrencyEntity> {

        val outputList: MutableList<CurrencyEntity> = ArrayList()
        for (i in inputList.indices) {
            val currency = CurrencyEntity()
            currency.nominal = inputList[i]?.nominal ?: 0
            currency.value = inputList[i]?.value ?: 0.0
            currency.name = inputList[i]?.name ?: ""
            currency.charCode = inputList[i]?.charCode ?: ""

            outputList.add(currency)
        }
        return outputList
    }
}