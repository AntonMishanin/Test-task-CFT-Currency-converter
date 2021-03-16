package com.example.cft_converter.data.mapper

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.google.gson.JsonObject

class JsonMapper {

    fun invoke(jsonObject: JsonObject): List<CurrencyEntity> {
        val asJson = jsonObject.getAsJsonObject("Valute")

        val outputList: MutableList<CurrencyEntity> = ArrayList()
        for (key in asJson.keySet()) {
            val valute = CurrencyEntity(
                asJson[key].asJsonObject.getAsJsonPrimitive("CharCode").asString,
                asJson[key].asJsonObject.getAsJsonPrimitive("Nominal").asInt,
                asJson[key].asJsonObject.getAsJsonPrimitive("Name").asString,
                asJson[key].asJsonObject.getAsJsonPrimitive("Value").asDouble,
            )
            outputList.add(valute)
        }
        return outputList
    }
}