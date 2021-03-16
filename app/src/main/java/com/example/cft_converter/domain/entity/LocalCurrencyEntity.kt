package com.example.cft_converter.domain.entity

import io.realm.RealmObject

open class LocalCurrencyEntity (
    var charCode: String? = "",
    var nominal: Int? = 0,
    var name: String? = "",
    var value: Double? = 0.0
): RealmObject()