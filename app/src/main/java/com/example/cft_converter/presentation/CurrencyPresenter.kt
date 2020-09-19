package com.example.cft_converter.presentation

import com.example.cft_converter.data.network.CurrencyApi
import com.example.cft_converter.domain.CurrencyUseCase
import com.example.cft_converter.domain.callback.NetworkCallback
import com.example.cft_converter.domain.entity.CurrencyBody
import com.example.cft_converter.domain.entity.CurrencyEntityNetwork

class CurrencyPresenter(
    private val view: CurrencyView,
    private val api: CurrencyApi,
    private val useCase: CurrencyUseCase
) {

    private lateinit var valutes: List<CurrencyBody>
    private var selectCurrency = 0

    fun onViewCreated() {
        view.initView()

        useCase.requestListCurrency(api, object : NetworkCallback {
            override fun onSuccess(listValute: List<CurrencyBody>) {
                view.setListCurrency(listValute)
                valutes = listValute
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
            }

            1 -> {
                val charCode = valutes[position].CharCode
                view.setOutputCurrencyCharCode(charCode)
            }
        }
        view.hideDialog()
    }

    fun onCurrencyTextChanged(inputValue: String) {
        view.setCurrencyValue(inputValue)
    }

    fun onClickSelectInputCurrency() {
        selectCurrency = 0
        view.showDialog()
    }

    fun onClickSelectOutputCurrency() {
        selectCurrency = 1
        view.showDialog()
    }
}