package com.example.cft_converter.presenters


import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.domain.use_case.ConvertCurrencyUseCase
import com.example.cft_converter.domain.use_case.RequestFreshListOfCurrenciesUseCase
import com.example.cft_converter.domain.use_case.RequestListOfCurrenciesUseCase
import com.example.cft_converter.utils.Constants.Companion.SELECT_FIRST_VALUTE
import com.example.cft_converter.utils.Constants.Companion.SELECT_SECOND_VALUTE
import com.example.cft_converter.utils.toStringWithDot
import com.example.cft_converter.utils.toValidDouble
import io.reactivex.disposables.CompositeDisposable

import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
open class CurrencyPresenter(
    private val requestListOfCurrenciesUseCase: RequestListOfCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val requestFreshListOfCurrenciesUseCase: RequestFreshListOfCurrenciesUseCase
) : MvpPresenter<CurrencyView>() {

    private var compositeDisposable: CompositeDisposable? = null

    private lateinit var listOfCurrencyEntities: List<CurrencyEntity>
    private var selectCurrency = 0

    private var inputCurrency = CurrencyEntity()
    private var outputCurrency = CurrencyEntity()
    private var inputValue = 0.0
    private var inputCurrencyValueFromUser = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        compositeDisposable = CompositeDisposable()

        viewState.initView()

        viewState?.showProgressBar()
        val disposable = requestListOfCurrenciesUseCase({ inputListOfCurrencies ->
            if (inputListOfCurrencies.isEmpty()) {
                viewState.showFailLayout()
            } else {
                viewState.hideFailLayout()

                viewState.setListOfCurrencies(inputListOfCurrencies)
                listOfCurrencyEntities = inputListOfCurrencies

                inputCurrency = inputListOfCurrencies[0]
                val charCode1 = listOfCurrencyEntities[0].charCode
                viewState.setInputCurrencyCharCode(charCode1)
                inputCurrency = listOfCurrencyEntities[0]

                outputCurrency = inputListOfCurrencies[1]
                val charCode2 = listOfCurrencyEntities[1].charCode
                viewState.setOutputCurrencyCharCode(charCode2)
                outputCurrency = listOfCurrencyEntities[1]

                viewState.setInputCurrencyValue("1")
            }
            viewState?.hideProgressBar()
        }, { error ->
            error.printStackTrace()
            viewState?.hideProgressBar()
        })

        compositeDisposable?.add(disposable)
    }

    fun onDestroyView() {
        compositeDisposable?.clear()
    }

    fun onItemCurrencyClick(position: Int) {
        when (selectCurrency) {

            SELECT_FIRST_VALUTE -> {
                val charCode = listOfCurrencyEntities[position].charCode
                viewState.setInputCurrencyCharCode(charCode)
                inputCurrency = listOfCurrencyEntities[position]
            }

            SELECT_SECOND_VALUTE -> {
                val charCode = listOfCurrencyEntities[position].charCode
                viewState.setOutputCurrencyCharCode(charCode)
                outputCurrency = listOfCurrencyEntities[position]
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
                    inputCurrency.value,
                    inputCurrency.nominal,
                    outputCurrency.value,
                    outputCurrency.nominal
                )
                viewState.setOutputCurrencyValue(outputCurrencyValue.toStringWithDot())
            }

            SELECT_SECOND_VALUTE -> {
                val outputCurrencyValue = convertCurrencyUseCase(
                    inputValue,
                    outputCurrency.value,
                    outputCurrency.nominal,
                    inputCurrency.value,
                    inputCurrency.nominal
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

    private fun requestFreshContent() {
        viewState?.showProgressBar()
        requestFreshListOfCurrenciesUseCase { error ->
            error.printStackTrace()
            viewState?.hideProgressBar()
        }
    }
}