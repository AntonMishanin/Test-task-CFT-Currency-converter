package com.example.cft_converter.data.mapper

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.data.dto.LocalCurrencyDto
import io.realm.RealmResults

class CurrencyMapper {

    fun mapping(inputListOfCurrencies: RealmResults<LocalCurrencyDto>): List<CurrencyEntity> {

        val outputListOfCurrencies: MutableList<CurrencyEntity> = ArrayList()
        for (i in inputListOfCurrencies.indices) {
            val currency = CurrencyEntity()
            currency.nominal = inputListOfCurrencies[i]?.nominal ?: 0
            currency.value = inputListOfCurrencies[i]?.value ?: 0.0
            currency.name = inputListOfCurrencies[i]?.name ?: ""
            currency.charCode = inputListOfCurrencies[i]?.charCode ?: ""

            outputListOfCurrencies.add(currency)
        }
        return outputListOfCurrencies
    }
}