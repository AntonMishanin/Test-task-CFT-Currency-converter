package com.example.cft_converter.presentation

import com.example.cft_converter.domain.entity.CurrencyBody

interface CurrencyView {

    fun initView()

    fun setListCurrency(listValute: List<CurrencyBody>)

    fun showDialog()

    fun hideDialog()

    fun setInputCurrencyValue(currencyValue: String)

    fun setOutputCurrencyValue(currencyValue: String)

    fun setInputCurrencyCharCode(charCode: String)

    fun setOutputCurrencyCharCode(charCode: String)
}