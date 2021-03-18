package com.example.cft_converter.domain.entity

data class CurrencyEntity(
    var charCode: String = "",
    var nominal: Int = 0,
    var name: String = "",
    var value: Double = 0.0
)