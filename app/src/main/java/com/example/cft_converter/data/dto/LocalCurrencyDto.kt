package com.example.cft_converter.data.dto

import io.realm.RealmObject

open class LocalCurrencyDto (
    var charCode: String? = "",
    var nominal: Int? = 0,
    var name: String? = "",
    var value: Double? = 0.0
): RealmObject()