package com.example.cft_converter.presenters

import com.example.cft_converter.domain.entity.CurrencyEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CurrencyView: MvpView {

    fun initView()

    fun setListOfCurrencies(listOfCurrencyEntities: List<CurrencyEntity>)

    fun showCurrencySelectionDialog()

    fun hideCurrencySelectionDialog()

    fun setInputCurrencyValue(currencyValue: String)

    fun setOutputCurrencyValue(currencyValue: String)

    fun setInputCurrencyCharCode(charCode: String)

    fun setOutputCurrencyCharCode(charCode: String)

    fun showFailLayout()

    fun hideFailLayout()

    fun showProgressBar()

    fun hideProgressBar()
}