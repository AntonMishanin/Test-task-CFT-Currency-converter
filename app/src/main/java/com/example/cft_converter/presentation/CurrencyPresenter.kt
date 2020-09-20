package com.example.cft_converter.presentation

import android.util.Log

import com.example.cft_converter.domain.CurrencyUseCase
import com.example.cft_converter.domain.callback.PresentationCallback
import com.example.cft_converter.domain.entity.CurrencyBody

import com.example.cft_converter.data.network.RetrofitService
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
open class CurrencyPresenter: MvpPresenter<CurrencyView>() {

   private val retrofit = RetrofitService()
   private val api = retrofit.provideCurrencyApi(retrofit.provideRetrofit())
   private val useCase = CurrencyUseCase()

    private lateinit var valutes: List<CurrencyBody>
    private var selectCurrency = 0

    private var inputCurrency =  CurrencyBody()
    private var outputCurrency = CurrencyBody()
    private var inputValue = 0.0
    private var inputCurrencyValueNow = false
    private var inputCurrencyValueChanged = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d("TAG", "onFirstViewAttach()")
        viewState.initView()

        useCase.requestListCurrencyFromDb(api, object : PresentationCallback {
            override fun onSuccess(listValute: List<CurrencyBody>) {
                Log.d("TAG", "listValute = ${listValute.size}")
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
            }

            override fun onError(message: String) {
Log.d("TAG", "message = $message")
            }
        })
    }

    fun onItemCurrencyClick(position: Int) {
        when (selectCurrency) {

            0 -> {
                val charCode = valutes[position].CharCode
                viewState.setInputCurrencyCharCode(charCode)
                inputCurrency = valutes[position]
            }

            1 -> {
                val charCode = valutes[position].CharCode
                viewState.setOutputCurrencyCharCode(charCode)
                outputCurrency = valutes[position]
            }
        }
        convertCurrency()
        viewState.hideDialog()
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
        viewState.showDialog()
    }

    fun onClickSelectOutputCurrency() {
        selectCurrency = 1
        viewState.showDialog()
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
            val value = String.format("%.3f", outputCurrencyValue)
            viewState.setOutputCurrencyValue(value)
        } else {
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
        inputCurrencyValueNow = false
    }

    fun onReloadCurrencyList() {
        Log.d("TAG", "onReloadCurrencyList() {")

        useCase.onReloadCurrencyList(api, object : PresentationCallback {
            override fun onSuccess(listValute: List<CurrencyBody>) {
                Log.d("TAG", "listValute() { ${listValute.size}")
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
            }

            override fun onError(message: String) {
                Log.d("TAG", "omessage $message")
            }
        })
    }
}