package com.example.cft_converter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.App
import com.example.cft_converter.R
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

    private lateinit var currencySelectionDialog: AlertDialog
    private var currencyAdapter: CurrencyAdapter? = null

    private var secondCharCodeView: TextView? = null
    private var firstCharCodeView: TextView? = null
    private var secondCurrencyInputField: EditText? = null
    private var firstCurrencyInputField: EditText? = null

    private var progressBar: ProgressBar? = null
    private var layoutFail: View? = null

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = (application as App).currencyComponent.getCurrencyPresenter()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyView()
    }

    override fun initView() {
        layoutFail = findViewById(R.id.layout_fail)

        secondCharCodeView = findViewById(R.id.second_currency_char_code)
        firstCharCodeView = findViewById(R.id.first_currency_char_code)

        initCurrencySelectionDialog()

        firstCurrencyInputField = findViewById(R.id.first_currency_input_field)
        firstCurrencyInputField?.afterTextChanged {
            presenter.onFirstInputCurrencyTextChanged(it)
        }

        secondCurrencyInputField = findViewById(R.id.second_currency_input_field)
        secondCurrencyInputField?.afterTextChanged {
            presenter.onSecondInputCurrencyTextChanged(it)
        }

        //View
        val selectFirstCurrency = findViewById<ImageButton>(R.id.select_first_currency)
        selectFirstCurrency?.setOnClickListener { presenter.onClickSelectCurrencyFromFirstInputField() }

        val selectSecondCurrency = findViewById<ImageButton>(R.id.select_second_currency)
        selectSecondCurrency?.setOnClickListener { presenter.onClickSelectCurrencyFromSecondInputField() }

        val reset = findViewById<ImageButton>(R.id.imageButton_reset_list)
        reset?.setOnClickListener { presenter.onClickResetListOfCurrencies() }

        val resetFromFailLayout = findViewById<Button>(R.id.reset_fail)
        resetFromFailLayout?.setOnClickListener { presenter.onClickResetFromFailLayout() }

        progressBar = findViewById(R.id.progress_bar)
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
        firstCurrencyInputField?.setText(currencyValue)
    }

    override fun setCurrencyValueInSecondInputField(currencyValue: String) {
        secondCurrencyInputField?.setText(currencyValue)
    }

    override fun setFirstCurrencyCharCode(charCode: String) {
        firstCharCodeView?.text = charCode
    }

    override fun setSecondCurrencyCharCode(charCode: String) {
        secondCharCodeView?.text = charCode
    }

    override fun showFailLayout() {
        layoutFail?.visible = true
    }

    override fun hideFailLayout() {
        layoutFail?.visible = false
    }

    override fun showProgressBar() {
        progressBar?.visible = true
    }

    override fun hideProgressBar() {
        progressBar?.visible = false
    }
}
