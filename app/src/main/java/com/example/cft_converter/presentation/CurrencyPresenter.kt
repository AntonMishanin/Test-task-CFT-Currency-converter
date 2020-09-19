package com.example.cft_converter.presentation

import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.domain.CurrencyUseCase
import com.example.cft_converter.domain.callback.NetworkCallback
import com.example.cft_converter.domain.entity.CurrencyBody

class CurrencyPresenter(
    private val view: CurrencyView,
    private val api: CurrencyApi,
    private val useCase: CurrencyUseCase
) {

    private lateinit var valutes: List<CurrencyBody>
    private var selectCurrency = 0

    private lateinit var inputCurrency: CurrencyBody
    private lateinit var outputCurrency: CurrencyBody
    private var inputValue = 0.0
    private var inputCurrencyValueNow = false
    private var inputCurrencyValueChanged = false

    fun onViewCreated() {
        view.initView()

        useCase.requestListCurrency(api, object : NetworkCallback {
            override fun onSuccess(listValute: List<CurrencyBody>) {
                view.setListCurrency(listValute)
                valutes = listValute

                inputCurrency = listValute[0]
                val charCode1 = valutes[0].CharCode
                view.setInputCurrencyCharCode(charCode1)
                inputCurrency = valutes[0]

                outputCurrency = listValute[1]
                val charCode2 = valutes[1].CharCode
                view.setOutputCurrencyCharCode(charCode2)
                outputCurrency = valutes[1]

                view.setInputCurrencyValue("1")
            }

            override fun onError(message: String?) {

            }
        })
    }

    fun onItemCurrencyClick(position: Int) {
        when (selectCurrency) {

            0 -> {
                val charCode = valutes[position].CharCode
                view.setInputCurrencyCharCode(charCode)
                inputCurrency = valutes[position]
            }

            1 -> {
                val charCode = valutes[position].CharCode
                view.setOutputCurrencyCharCode(charCode)
                outputCurrency = valutes[position]
            }
        }
        convertCurrency()
        view.hideDialog()
    }

    fun onInputCurrencyTextChanged(inputValue: String) {
        if (inputCurrencyValueNow) {
            return
        }

        try {
            this.inputValue = inputValue.toDouble()
            inputCurrencyValueChanged = true
            convertCurrency()
        } catch (e: NumberFormatException) {
        }
    }

    fun onOutputCurrencyTextChanged(inputValue: String) {
        if (inputCurrencyValueNow) {
            return
        }

        try {
            this.inputValue = inputValue.toDouble()
            inputCurrencyValueChanged = false
            convertCurrency()
        } catch (e: NumberFormatException) {
        }
    }

    fun onClickSelectInputCurrency() {
        selectCurrency = 0
        view.showDialog()
    }

    fun onClickSelectOutputCurrency() {
        selectCurrency = 1
        view.showDialog()
    }

    private fun convertCurrency() {
        inputCurrencyValueNow = true
        if (inputCurrencyValueChanged) {
            val outputCurrencyValue = useCase.convertCurrency(
                inputValue,
                inputCurrency.Value,
                inputCurrency.Nominal,
                outputCurrency.Value,
                outputCurrency.Nominal
            )
            view.setOutputCurrencyValue(outputCurrencyValue.toString())
        } else {
            val outputCurrencyValue = useCase.convertCurrency(
                inputValue,
                outputCurrency.Value,
                outputCurrency.Nominal,
                inputCurrency.Value,
                inputCurrency.Nominal
            )
            view.setInputCurrencyValue(outputCurrencyValue.toString())
        }
        inputCurrencyValueNow = false
    }
}