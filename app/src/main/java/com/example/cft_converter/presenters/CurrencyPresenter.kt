package com.example.cft_converter.presenters

import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.domain.use_case.ConvertCurrencyUseCase
import com.example.cft_converter.domain.use_case.RequestFreshListOfCurrenciesUseCase
import com.example.cft_converter.domain.use_case.RequestListOfCurrenciesUseCase
import com.example.cft_converter.domain.use_case.SaveCurrencyUseCase
import com.example.cft_converter.utils.Constants.Companion.DEFAULT_CURRENCY_VALUE
import com.example.cft_converter.utils.Constants.Companion.FIRST_DEFAULT_CURRENCY_ID
import com.example.cft_converter.utils.Constants.Companion.SECOND_DEFAULT_CURRENCY_ID
import com.example.cft_converter.utils.Constants.Companion.SELECT_CURRENCY_FROM_FIRST_INPUT_FIELD
import com.example.cft_converter.utils.Constants.Companion.SELECT_CURRENCY_FROM_SECOND_INPUT_FIELD
import com.example.cft_converter.utils.print
import com.example.cft_converter.utils.toStringWithDot
import com.example.cft_converter.utils.toValidDouble
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
open class CurrencyPresenter @Inject constructor(
    private val requestListOfCurrenciesUseCase: RequestListOfCurrenciesUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val requestFreshListOfCurrenciesUseCase: RequestFreshListOfCurrenciesUseCase,
    private val saveCurrencyUseCase: SaveCurrencyUseCase
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
        viewState?.hideFailLayout()
        viewState?.showProgressBar()

        requestFreshContent()

        val disposable = requestListOfCurrenciesUseCase({ listOfCurrencies ->
            onSuccessCurrencyDownload(listOfCurrencies)
        }, { error ->
            error.print()
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
            return
        }
        viewState?.hideProgressBar()
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
        if (!inputCurrencyValueFromUser) {
            return
        }
        this.inputValue = inputValue.toValidDouble()
        selectCurrencyFromField = SELECT_CURRENCY_FROM_FIRST_INPUT_FIELD
        convertCurrency()
    }

    fun onSecondInputCurrencyTextChanged(inputValue: CharSequence) {
        if (!inputCurrencyValueFromUser) {
            return
        }
        this.inputValue = inputValue.toValidDouble()
        selectCurrencyFromField = SELECT_CURRENCY_FROM_SECOND_INPUT_FIELD
        convertCurrency()
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

    fun onClickResetListOfCurrencies() = requestFreshContent()

    fun onCancelCurrencySelectionDialog() = viewState.hideCurrencySelectionDialog()

    fun onClickResetFromFailLayout() = requestFreshContent()

    private fun requestFreshContent() {
        viewState?.showProgressBar()
        viewState?.hideFailLayout()

        val subscriber = object : DisposableSingleObserver<JsonObject>() {
            override fun onSuccess(jsonObject: JsonObject) {
                saveCurrencyUseCase(jsonObject)
            }

            override fun onError(e: Throwable) {
                e.print()
                viewState?.hideProgressBar()
                viewState.showFailLayout()
            }
        }

        requestFreshListOfCurrenciesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)

        compositeDisposable?.add(subscriber)
    }
}