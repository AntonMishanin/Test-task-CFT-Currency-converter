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
import com.example.cft_converter.domain.entity.CurrencyBody
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
    private var adapter: CurrencyAdapter? = null

    private var outputCharCodeView: TextView? = null
    private var inputCharCodeView: TextView? = null
    private var outputCurrencyView: EditText? = null
    private var inputCurrencyView: EditText? = null

    private var progressBar: ProgressBar? = null

    private var layoutNoInternetConnection: View? = null

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
        layoutNoInternetConnection = findViewById(R.id.layout_no_internet_connection)

        outputCharCodeView = findViewById(R.id.textView_output_currency_char_code)
        inputCharCodeView = findViewById(R.id.textView_input_currency_char_code)

        initCurrencySelectionDialog()

        inputCurrencyView = findViewById(R.id.editText_currency_input)
        inputCurrencyView?.afterTextChanged {
            presenter.onInputCurrencyTextChanged(it)
        }

        outputCurrencyView = findViewById(R.id.textView_currency_output)
        outputCurrencyView?.afterTextChanged {
            presenter.onOutputCurrencyTextChanged(it)
        }

        //View
        val selectInputCurrency = findViewById<ImageButton>(R.id.imageButton_select_input_currency)
        selectInputCurrency?.setOnClickListener { presenter.onClickSelectInputCurrency() }

        val selectOutputCurrency =
            findViewById<ImageButton>(R.id.imageButton_select_output_currency)
        selectOutputCurrency?.setOnClickListener { presenter.onClickSelectOutputCurrency() }

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

        adapter = CurrencyAdapter { presenter.onItemCurrencyClick(it) }

        val currencyRecyclerView =
            currencySelectionView.findViewById<RecyclerView>(R.id.recyclerView_dialog_currency)
        currencyRecyclerView?.layoutManager = LinearLayoutManager(this)
        currencyRecyclerView?.adapter = adapter
    }

    override fun setListOfCurrencies(listOfCurrencies: List<CurrencyBody>) {
        adapter?.listOfCurrencies = listOfCurrencies
    }

    override fun showCurrencySelectionDialog() = currencySelectionDialog.show()

    override fun hideCurrencySelectionDialog() = currencySelectionDialog.dismiss()

    override fun setInputCurrencyValue(currencyValue: String) {
        inputCurrencyView?.setText(currencyValue)
    }

    override fun setOutputCurrencyValue(currencyValue: String) {
        outputCurrencyView?.setText(currencyValue)
    }

    override fun setInputCurrencyCharCode(charCode: String) {
        inputCharCodeView?.text = charCode
    }

    override fun setOutputCurrencyCharCode(charCode: String) {
        outputCharCodeView?.text = charCode
    }

    override fun showFailLayout() {
        layoutNoInternetConnection?.visible = true
    }

    override fun hideFailLayout() {
        layoutNoInternetConnection?.visible = false
    }

    override fun showProgressBar() {
        progressBar?.visible = true
    }

    override fun hideProgressBar() {
        progressBar?.visible = false
    }
}
