package com.example.cft_converter.presenters


import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.use_case.ConvertCurrencyUseCase
import com.example.cft_converter.domain.use_case.RequestFreshListOfCurrenciesUseCase
import com.example.cft_converter.domain.use_case.RequestListOfCurrenciesUseCase
import com.example.cft_converter.utils.Constants.Companion.SELECT_FIRST_VALUTE
import com.example.cft_converter.utils.Constants.Companion.SELECT_SECOND_VALUTE
import com.example.cft_converter.utils.toStringWithDot
import com.example.cft_converter.utils.toValidDouble

import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
open class CurrencyPresenter(
    private val requestListOfCurrenciesUseCase: RequestListOfCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val requestFreshListOfCurrenciesUseCase: RequestFreshListOfCurrenciesUseCase
) : MvpPresenter<CurrencyView>() {

    private lateinit var listOfCurrencies: List<CurrencyBody>
    private var selectCurrency = 0

    private var inputCurrency = CurrencyBody()
    private var outputCurrency = CurrencyBody()
    private var inputValue = 0.0
    private var inputCurrencyValueFromUser = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()

        viewState?.showProgressBar()
        requestListOfCurrenciesUseCase({ inputListOfCurrencies ->
            if (inputListOfCurrencies.isEmpty()) {
                viewState.showFailLayout()
            } else {
                viewState.hideFailLayout()

                viewState.setListOfCurrencies(inputListOfCurrencies)
                listOfCurrencies = inputListOfCurrencies

                inputCurrency = inputListOfCurrencies[0]
                val charCode1 = listOfCurrencies[0].CharCode
                viewState.setInputCurrencyCharCode(charCode1)
                inputCurrency = listOfCurrencies[0]

                outputCurrency = inputListOfCurrencies[1]
                val charCode2 = listOfCurrencies[1].CharCode
                viewState.setOutputCurrencyCharCode(charCode2)
                outputCurrency = listOfCurrencies[1]

                viewState.setInputCurrencyValue("1")
            }
            viewState?.hideProgressBar()
        }, { error ->
            error.printStackTrace()
            viewState?.hideProgressBar()
        })
    }

    fun onItemCurrencyClick(position: Int) {
        when (selectCurrency) {

            SELECT_FIRST_VALUTE -> {
                val charCode = listOfCurrencies[position].CharCode
                viewState.setInputCurrencyCharCode(charCode)
                inputCurrency = listOfCurrencies[position]
            }

            SELECT_SECOND_VALUTE -> {
                val charCode = listOfCurrencies[position].CharCode
                viewState.setOutputCurrencyCharCode(charCode)
                outputCurrency = listOfCurrencies[position]
            }
        }
        convertCurrency()
        viewState.hideCurrencySelectionDialog()
    }

    fun onInputCurrencyTextChanged(inputValue: CharSequence) {
        if (inputCurrencyValueFromUser) {
            this.inputValue = inputValue.toValidDouble()
            selectCurrency = SELECT_FIRST_VALUTE
            convertCurrency()
        }
    }

    fun onOutputCurrencyTextChanged(inputValue: CharSequence) {
        if (inputCurrencyValueFromUser) {
            this.inputValue = inputValue.toValidDouble()
            selectCurrency = SELECT_SECOND_VALUTE
            convertCurrency()
        }
    }

    fun onClickSelectInputCurrency() {
        selectCurrency = SELECT_FIRST_VALUTE
        viewState.showCurrencySelectionDialog()
    }

    fun onClickSelectOutputCurrency() {
        selectCurrency = SELECT_SECOND_VALUTE
        viewState.showCurrencySelectionDialog()
    }

    private fun convertCurrency() {
        inputCurrencyValueFromUser = false

        when (selectCurrency) {
            SELECT_FIRST_VALUTE -> {
                val outputCurrencyValue = convertCurrencyUseCase(
                    inputValue,
                    inputCurrency.Value,
                    inputCurrency.Nominal,
                    outputCurrency.Value,
                    outputCurrency.Nominal
                )
                viewState.setOutputCurrencyValue(outputCurrencyValue.toStringWithDot())
            }

            SELECT_SECOND_VALUTE -> {
                val outputCurrencyValue = convertCurrencyUseCase(
                    inputValue,
                    outputCurrency.Value,
                    outputCurrency.Nominal,
                    inputCurrency.Value,
                    inputCurrency.Nominal
                )
                viewState.setInputCurrencyValue(outputCurrencyValue.toStringWithDot())
            }
        }

        inputCurrencyValueFromUser = true
    }

    fun onClickResetListOfCurrencies() {
        requestFreshContent()
    }

    fun onCancelCurrencySelectionDialog() {
        viewState.hideCurrencySelectionDialog()
    }

    fun onClickResetFromFailLayout() {
        requestFreshContent()
    }

    private fun requestFreshContent(){
        viewState?.showProgressBar()
        requestFreshListOfCurrenciesUseCase { error ->
            error.printStackTrace()
            viewState?.hideProgressBar()
        }
    }
}
/*
if (listValute.isNotEmpty()) {
                viewState.setListOfCurrencies(listValute)
                listOfCurrencies = listValute

                inputCurrency = listValute[0]
                val charCode1 = listOfCurrencies[0].CharCode
                viewState.setInputCurrencyCharCode(charCode1)
                inputCurrency = listOfCurrencies[0]

                outputCurrency = listValute[1]
                val charCode2 = listOfCurrencies[1].CharCode
                viewState.setOutputCurrencyCharCode(charCode2)
                outputCurrency = listOfCurrencies[1]

                viewState.setInputCurrencyValue("1")

                viewState?.hideProgressBar()
            }
 */