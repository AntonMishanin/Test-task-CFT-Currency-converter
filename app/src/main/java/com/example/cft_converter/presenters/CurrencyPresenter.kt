package com.example.cft_converter.presenters

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.domain.use_case.ConvertCurrencyUseCase
import com.example.cft_converter.domain.use_case.RequestFreshListOfCurrenciesUseCase
import com.example.cft_converter.domain.use_case.RequestListOfCurrenciesUseCase
import com.example.cft_converter.utils.Constants.Companion.DEFAULT_CURRENCY_VALUE
import com.example.cft_converter.utils.Constants.Companion.FIRST_DEFAULT_CURRENCY_ID
import com.example.cft_converter.utils.Constants.Companion.SECOND_DEFAULT_CURRENCY_ID
import com.example.cft_converter.utils.Constants.Companion.SELECT_CURRENCY_FROM_FIRST_INPUT_FIELD
import com.example.cft_converter.utils.Constants.Companion.SELECT_CURRENCY_FROM_SECOND_INPUT_FIELD
import com.example.cft_converter.utils.toStringWithDot
import com.example.cft_converter.utils.toValidDouble
import io.reactivex.disposables.CompositeDisposable

import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
open class CurrencyPresenter @Inject constructor(
    private val requestListOfCurrenciesUseCase: RequestListOfCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val requestFreshListOfCurrenciesUseCase: RequestFreshListOfCurrenciesUseCase
) : MvpPresenter<CurrencyView>() {

    private var compositeDisposable: CompositeDisposable? = null

    private lateinit var listOfCurrencies: List<CurrencyEntity>
    private var selectCurrencyFromField = SELECT_CURRENCY_FROM_FIRST_INPUT_FIELD

    private var currencyInFirstInputField = CurrencyEntity()
    private var currencyInSecondInputField = CurrencyEntity()
    private var inputValue = 0.0
    private var inputCurrencyValueFromUser = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        compositeDisposable = CompositeDisposable()

        viewState.initView()
        viewState?.showProgressBar()

        val disposable = requestListOfCurrenciesUseCase({ listOfCurrencies ->
            onSuccessCurrencyDownload(listOfCurrencies)
        }, { error ->
            error.printStackTrace()
            viewState?.hideProgressBar()
            viewState.showFailLayout()
        })

        compositeDisposable?.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }

    private fun onSuccessCurrencyDownload(listOfCurrencies: List<CurrencyEntity>) {
        if (listOfCurrencies.isEmpty()) {
            viewState.showFailLayout()
        } else {
            viewState.hideFailLayout()

            viewState.setListOfCurrencies(listOfCurrencies)
            this.listOfCurrencies = listOfCurrencies

            //On first download set currency into input field
            currencyInFirstInputField = listOfCurrencies[FIRST_DEFAULT_CURRENCY_ID]
            val firstCharCode = this.listOfCurrencies[FIRST_DEFAULT_CURRENCY_ID].charCode
            viewState.setFirstCurrencyCharCode(firstCharCode)
            currencyInFirstInputField = this.listOfCurrencies[FIRST_DEFAULT_CURRENCY_ID]

            currencyInSecondInputField = listOfCurrencies[SECOND_DEFAULT_CURRENCY_ID]
            val secondCharCode = this.listOfCurrencies[SECOND_DEFAULT_CURRENCY_ID].charCode
            viewState.setSecondCurrencyCharCode(secondCharCode)
            currencyInSecondInputField = this.listOfCurrencies[SECOND_DEFAULT_CURRENCY_ID]

            viewState.setCurrencyValueInFirstInputField(DEFAULT_CURRENCY_VALUE)
        }
        viewState?.hideProgressBar()
    }

    fun onClickItemCurrency(position: Int) {
        when (selectCurrencyFromField) {

            SELECT_CURRENCY_FROM_FIRST_INPUT_FIELD -> {
                val charCode = listOfCurrencies[position].charCode
                viewState.setFirstCurrencyCharCode(charCode)
                currencyInFirstInputField = listOfCurrencies[position]
            }

            SELECT_CURRENCY_FROM_SECOND_INPUT_FIELD -> {
                val charCode = listOfCurrencies[position].charCode
                viewState.setSecondCurrencyCharCode(charCode)
                currencyInSecondInputField = listOfCurrencies[position]
            }
        }
        convertCurrency()
        viewState.hideCurrencySelectionDialog()
    }

    fun onFirstInputCurrencyTextChanged(inputValue: CharSequence) {
        if (inputCurrencyValueFromUser) {
            this.inputValue = inputValue.toValidDouble()
            selectCurrencyFromField = SELECT_CURRENCY_FROM_FIRST_INPUT_FIELD
            convertCurrency()
        }
    }

    fun onSecondInputCurrencyTextChanged(inputValue: CharSequence) {
        if (inputCurrencyValueFromUser) {
            this.inputValue = inputValue.toValidDouble()
            selectCurrencyFromField = SELECT_CURRENCY_FROM_SECOND_INPUT_FIELD
            convertCurrency()
        }
    }

    fun onClickSelectCurrencyFromFirstInputField() {
        selectCurrencyFromField = SELECT_CURRENCY_FROM_FIRST_INPUT_FIELD
        viewState.showCurrencySelectionDialog()
    }

    fun onClickSelectCurrencyFromSecondInputField() {
        selectCurrencyFromField = SELECT_CURRENCY_FROM_SECOND_INPUT_FIELD
        viewState.showCurrencySelectionDialog()
    }

    private fun convertCurrency() {
        inputCurrencyValueFromUser = false

        when (selectCurrencyFromField) {
            SELECT_CURRENCY_FROM_FIRST_INPUT_FIELD -> {
                val convertedCurrencyValue = convertCurrencyUseCase(
                    inputValue,
                    currencyInFirstInputField.value,
                    currencyInFirstInputField.nominal,
                    currencyInSecondInputField.value,
                    currencyInSecondInputField.nominal
                )
                viewState.setCurrencyValueInSecondInputField(convertedCurrencyValue.toStringWithDot())
            }

            SELECT_CURRENCY_FROM_SECOND_INPUT_FIELD -> {
                val convertedCurrencyValue = convertCurrencyUseCase(
                    inputValue,
                    currencyInSecondInputField.value,
                    currencyInSecondInputField.nominal,
                    currencyInFirstInputField.value,
                    currencyInFirstInputField.nominal
                )
                viewState.setCurrencyValueInFirstInputField(convertedCurrencyValue.toStringWithDot())
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