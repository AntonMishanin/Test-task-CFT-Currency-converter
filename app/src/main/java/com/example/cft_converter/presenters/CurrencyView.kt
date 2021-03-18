package com.example.cft_converter.presenters

import com.example.cft_converter.domain.entity.CurrencyEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CurrencyView: MvpView {

    fun initView()

    fun setListOfCurrencies(listOfCurrency: List<CurrencyEntity>)

    fun showCurrencySelectionDialog()

    fun hideCurrencySelectionDialog()

    fun setCurrencyValueInFirstInputField(currencyValue: String)

    fun setCurrencyValueInSecondInputField(currencyValue: String)

    fun setFirstCurrencyCharCode(charCode: String)

    fun setSecondCurrencyCharCode(charCode: String)

    fun showFailLayout()

    fun hideFailLayout()

    fun showProgressBar()

    fun hideProgressBar()
}