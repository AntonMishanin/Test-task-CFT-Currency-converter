package com.example.cft_converter.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.App
import com.example.cft_converter.R
import com.example.cft_converter.databinding.ActivityCurrencyBinding
import com.example.cft_converter.databinding.LayoutFailBinding
import com.example.cft_converter.domain.entity.CurrencyEntity
import com.example.cft_converter.presenters.CurrencyPresenter
import com.example.cft_converter.presenters.CurrencyView
import com.example.cft_converter.utils.afterTextChanged
import com.example.cft_converter.utils.visible
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CurrencyActivity : MvpAppCompatActivity(), CurrencyView {

    @InjectPresenter
    lateinit var presenter: CurrencyPresenter

    private var binding: ActivityCurrencyBinding? = null
    private var bindingFail: LayoutFailBinding? = null

    private lateinit var currencySelectionDialog: AlertDialog
    private var currencyAdapter: CurrencyAdapter? = null

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = (application as App).currencyComponent.getCurrencyPresenter()

        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        bindingFail = LayoutFailBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        bindingFail = null
    }

    override fun initView() {
        binding?.apply {
            firstCurrencyInputField.afterTextChanged { presenter.onFirstInputCurrencyTextChanged(it) }

            secondCurrencyInputField.afterTextChanged {
                presenter.onSecondInputCurrencyTextChanged(it)
            }

            selectFirstCurrency.setOnClickListener { presenter.onClickSelectCurrencyFromFirstInputField() }
            selectSecondCurrency.setOnClickListener { presenter.onClickSelectCurrencyFromSecondInputField() }

            resetList.setOnClickListener { presenter.onClickResetListOfCurrencies() }
        }
        bindingFail?.resetFailLayout?.setOnClickListener { presenter.onClickResetFromFailLayout() }

        initCurrencySelectionDialog()
    }

    private fun initCurrencySelectionDialog() {
        currencySelectionDialog = AlertDialog.Builder(
            this,
            android.R.style.Theme_Material_Light_Dialog
        ).create()

        val currencySelectionView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_list_currency, null, false)
        currencySelectionDialog.setView(currencySelectionView)

        currencySelectionDialog.setOnCancelListener { presenter.onCancelCurrencySelectionDialog() }

        currencyAdapter = CurrencyAdapter { presenter.onClickItemCurrency(it) }

        val currencyRecyclerView =
            currencySelectionView.findViewById<RecyclerView>(R.id.recyclerView_list_of_currencies)
        currencyRecyclerView?.layoutManager = LinearLayoutManager(this)
        currencyRecyclerView?.adapter = currencyAdapter
    }

    override fun setListOfCurrencies(listOfCurrency: List<CurrencyEntity>) {
        currencyAdapter?.listOfCurrency = listOfCurrency
    }

    override fun showCurrencySelectionDialog() = currencySelectionDialog.show()

    override fun hideCurrencySelectionDialog() = currencySelectionDialog.dismiss()

    override fun setCurrencyValueInFirstInputField(currencyValue: String) {
        binding?.firstCurrencyInputField?.setText(currencyValue)
    }

    override fun setCurrencyValueInSecondInputField(currencyValue: String) {
        binding?.secondCurrencyInputField?.setText(currencyValue)
    }

    override fun setFirstCurrencyCharCode(charCode: String) {
        binding?.firstCurrencyCharCode?.text = charCode
    }

    override fun setSecondCurrencyCharCode(charCode: String) {
        binding?.secondCurrencyCharCode?.text = charCode
    }

    override fun showFailLayout() {
        bindingFail?.resetFailLayout?.visible = true
    }

    override fun hideFailLayout() {
        bindingFail?.resetFailLayout?.visible = false
    }

    override fun showProgressBar() {
        binding?.progressBar?.visible = true
    }

    override fun hideProgressBar() {
        binding?.progressBar?.visible = false
    }
}