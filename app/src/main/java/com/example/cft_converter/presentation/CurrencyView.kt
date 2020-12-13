package com.example.cft_converter.presentation

import com.example.cft_converter.domain.entity.CurrencyBody
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CurrencyView: MvpView {

    fun initView()

    fun setListCurrency(listValute: List<CurrencyBody>)

    fun showDialog()

    fun hideDialog()

    fun setInputCurrencyValue(currencyValue: String)

    fun setOutputCurrencyValue(currencyValue: String)

    fun setInputCurrencyCharCode(charCode: String)

    fun setOutputCurrencyCharCode(charCode: String)

    fun showListLoadingError(errorMessage: String)

}