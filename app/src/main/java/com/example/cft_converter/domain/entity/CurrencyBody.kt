package com.example.cft_converter.domain.entity

data class CurrencyBody(
    var ID: String = "",
    var NumCode: Int = 0,
    var CharCode: String = "",
    var Nominal: Int = 0,
    var Name: String = "",
    var Value: Double = 0.0,
    var Previous: Double = 0.0
)