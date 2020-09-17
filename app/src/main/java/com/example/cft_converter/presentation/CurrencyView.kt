package com.example.cft_converter.presentation

interface CurrencyView {

    fun initView()

    fun setListCurrency()

    fun showDialog()

    fun hideDialog()

    fun setCurrencyValue(currencyValue: String)
}