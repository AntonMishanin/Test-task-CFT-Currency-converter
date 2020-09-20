package com.example.cft_converter.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.domain.entity.CurrencyBody
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class CurrencyActivity : MvpAppCompatActivity(), CurrencyView, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: CurrencyPresenter

    private lateinit var currencyListAlertDialog: AlertDialog
    private var adapter = CurrencyAdapter()

    @ProvidePresenter
    fun providePresenter() = CurrencyPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.imageButton_select_input_currency -> {
                presenter.onClickSelectInputCurrency()
            }

            R.id.imageButton_select_output_currency -> {
                presenter.onClickSelectOutputCurrency()
            }

            R.id.imageButton_reload_list -> {
                presenter.onReloadCurrencyList()
            }
        }
    }

    @SuppressLint("InflateParams")
    @Suppress("DEPRECATION")
    override fun initView() {
        //Dialog
        currencyListAlertDialog = AlertDialog.Builder(
            this,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ).create()

        val currencyListView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_list_currency, null, false)
        currencyListAlertDialog.setView(currencyListView)

        //RecyclerView
        val recyclerViewOrderDetail =
            currencyListView.findViewById<RecyclerView>(R.id.recyclerView_dialog_currency)
        recyclerViewOrderDetail?.layoutManager = LinearLayoutManager(this)
        recyclerViewOrderDetail?.adapter = adapter

        adapter.setListener(object : CurrencyClickListener {
            override fun onItemClick(position: Int) {
                presenter.onItemCurrencyClick(position)
            }
        })

        //TextChangedListener
        val inputCurrencyView = findViewById<EditText>(R.id.editText_currency_input)
        inputCurrencyView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val inputValue = inputCurrencyView.text.toString()
                presenter.onInputCurrencyTextChanged(inputValue)
            }
        })

        val outputCurrencyView = findViewById<EditText>(R.id.textView_currency_output)
        outputCurrencyView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val inputValue = outputCurrencyView.text.toString()
                presenter.onOutputCurrencyTextChanged(inputValue)
            }
        })

        //View
        findViewById<ImageButton>(R.id.imageButton_select_input_currency).setOnClickListener(this)
        findViewById<ImageButton>(R.id.imageButton_select_output_currency).setOnClickListener(this)
        findViewById<ImageButton>(R.id.imageButton_reload_list).setOnClickListener(this)
    }

    override fun setListCurrency(listValute: List<CurrencyBody>) {
        runOnUiThread {
            adapter.setListCurrency(listValute)
        }
    }

    override fun showDialog() {
        currencyListAlertDialog.show()
    }

    override fun hideDialog() {
        currencyListAlertDialog.hide()
    }

    override fun setInputCurrencyValue(currencyValue: String) {
        Log.d("TAG", "setInputCurrencyValue")
        runOnUiThread {
            val inputCurrencyView = findViewById<EditText>(R.id.editText_currency_input)
            inputCurrencyView.setText(currencyValue)
        }
    }

    override fun setOutputCurrencyValue(currencyValue: String) {
        runOnUiThread {
            val currencyOutputView = findViewById<EditText>(R.id.textView_currency_output)
            currencyOutputView.setText(currencyValue)
        }
    }

    override fun setInputCurrencyCharCode(charCode: String) {
        runOnUiThread {
            val inputCharCodeView = findViewById<TextView>(R.id.textView_input_currency_char_code)
            inputCharCodeView.text = charCode
        }
    }

    override fun setOutputCurrencyCharCode(charCode: String) {
        runOnUiThread {
            val outputCharCodeView = findViewById<TextView>(R.id.textView_output_currency_char_code)
            outputCharCodeView.text = charCode
        }
    }

    override fun showInputError() {
        val inputErrorView = findViewById<TextView>(R.id.textView_input_error)
        inputErrorView.visibility = View.VISIBLE
    }

    override fun hideInputError() {
        val inputErrorView = findViewById<TextView>(R.id.textView_input_error)
        inputErrorView.visibility = View.GONE
    }
}
