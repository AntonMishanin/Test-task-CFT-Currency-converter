package com.example.cft_converter.presentation


import com.example.cft_converter.domain.RequestListCurrencyUseCase
import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.utils.Constants.Companion.SELECT_FIRST_VALUTE
import com.example.cft_converter.utils.Constants.Companion.SELECT_SECOND_VALUTE

import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
open class CurrencyPresenter(
    private val useCase: RequestListCurrencyUseCase
) : MvpPresenter<CurrencyView>() {

    private lateinit var valutes: List<CurrencyBody>
    private var selectCurrency = 0

    private var inputCurrency = CurrencyBody()
    private var outputCurrency = CurrencyBody()
    private var inputValue = 0.0
    private var inputFromUser = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.initView()

        useCase.fromDb({ listValute ->
            viewState.setListCurrency(listValute)
            valutes = listValute

            inputCurrency = listValute[0]
            val charCode1 = valutes[0].CharCode
            viewState.setInputCurrencyCharCode(charCode1)
            inputCurrency = valutes[0]

            outputCurrency = listValute[1]
            val charCode2 = valutes[1].CharCode
            viewState.setOutputCurrencyCharCode(charCode2)
            outputCurrency = valutes[1]

            viewState.setInputCurrencyValue("1")
        }, { message ->
            viewState.showListLoadingError(message)
        })


    }

    fun onItemCurrencyClick(position: Int) {
        when (selectCurrency) {

            SELECT_FIRST_VALUTE -> {
                val charCode = valutes[position].CharCode
                viewState.setInputCurrencyCharCode(charCode)
                inputCurrency = valutes[position]
            }

            SELECT_SECOND_VALUTE -> {
                val charCode = valutes[position].CharCode
                viewState.setOutputCurrencyCharCode(charCode)
                outputCurrency = valutes[position]
            }
        }
        convertCurrency()
        viewState.hideDialog()
    }

    fun onInputCurrencyTextChanged(inputValue: CharSequence) {
        if (inputFromUser) {

            this.inputValue = if (inputValue.toString() == "") {
                0.0
            } else {
                inputValue.toString().toDouble()
            }

            selectCurrency = 0
            convertCurrency()
        }
    }

    fun onOutputCurrencyTextChanged(inputValue: CharSequence) {
        if (inputFromUser) {

            this.inputValue = if (inputValue.toString() == "") {
                0.0
            } else {
                inputValue.toString().toDouble()
            }
            selectCurrency = 1
            convertCurrency()
        }
    }

    fun onClickSelectInputCurrency() {
        selectCurrency = 0
        viewState.showDialog()
    }

    fun onClickSelectOutputCurrency() {
        selectCurrency = 1
        viewState.showDialog()
    }

    private fun convertCurrency() {
        inputFromUser = false

        when (selectCurrency) {
            SELECT_FIRST_VALUTE -> {
                val outputCurrencyValue = useCase.convertCurrency(
                    inputValue,
                    inputCurrency.Value,
                    inputCurrency.Nominal,
                    outputCurrency.Value,
                    outputCurrency.Nominal
                )
                val value = String.format("%.3f", outputCurrencyValue)
                viewState.setOutputCurrencyValue(value)
            }

            SELECT_SECOND_VALUTE -> {
                val outputCurrencyValue = useCase.convertCurrency(
                    inputValue,
                    outputCurrency.Value,
                    outputCurrency.Nominal,
                    inputCurrency.Value,
                    inputCurrency.Nominal
                )
                val value = String.format("%.3f", outputCurrencyValue)
                viewState.setInputCurrencyValue(value)
            }
        }

        inputFromUser = true
    }

    fun onReloadCurrencyList() {
        useCase.reload({ listValute ->
            viewState.setListCurrency(listValute)
            valutes = listValute

            inputCurrency = listValute[0]
            val charCode1 = valutes[0].CharCode
            viewState.setInputCurrencyCharCode(charCode1)
            inputCurrency = valutes[0]

            outputCurrency = listValute[1]
            val charCode2 = valutes[1].CharCode
            viewState.setOutputCurrencyCharCode(charCode2)
            outputCurrency = valutes[1]

            viewState.setInputCurrencyValue("1")
        }, { message ->
            viewState.showListLoadingError(message)
        })
    }

    fun hideDialog() {
        viewState.hideDialog()
    }
}