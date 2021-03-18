package com.example.cft_converter.data.mapper

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.utils.Constants.Companion.JSON_FIELD_CHAR_CODE
import com.example.cft_converter.utils.Constants.Companion.JSON_FIELD_NAME
import com.example.cft_converter.utils.Constants.Companion.JSON_FIELD_NOMINAL
import com.example.cft_converter.utils.Constants.Companion.JSON_FIELD_VALUE
import com.example.cft_converter.utils.Constants.Companion.JSON_FIELD_VALUTE
import com.google.gson.JsonObject
import javax.inject.Inject

class JsonMapper @Inject constructor() {

    fun invoke(jsonObject: JsonObject): List<CurrencyEntity> {
        val asJson = jsonObject.getAsJsonObject(JSON_FIELD_VALUTE)

        val outputListOfCurrency: MutableList<CurrencyEntity> = ArrayList()
        for (key in asJson.keySet()) {
            val valute = CurrencyEntity(
                asJson[key].asJsonObject.getAsJsonPrimitive(JSON_FIELD_CHAR_CODE).asString,
                asJson[key].asJsonObject.getAsJsonPrimitive(JSON_FIELD_NOMINAL).asInt,
                asJson[key].asJsonObject.getAsJsonPrimitive(JSON_FIELD_NAME).asString,
                asJson[key].asJsonObject.getAsJsonPrimitive(JSON_FIELD_VALUE).asDouble,
            )
            outputListOfCurrency.add(valute)
        }
        return outputListOfCurrency
    }
}