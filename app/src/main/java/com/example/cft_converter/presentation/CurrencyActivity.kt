package com.example.cft_converter.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.data.network.RetrofitService
import com.example.cft_converter.domain.CurrencyUseCase
import com.example.cft_converter.domain.entity.CurrencyBody

class CurrencyActivity : AppCompatActivity(), CurrencyView, View.OnClickListener {

    private val retrofit = RetrofitService()
    private val api = retrofit.provideCurrencyApi(retrofit.provideRetrofit())
    private val useCase = CurrencyUseCase()
    private val presenter = CurrencyPresenter(this, api, useCase)

    private lateinit var currencyListAlertDialog: AlertDialog
    private lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        presenter.onViewCreated()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.imageButton_select_input_currency -> {
                presenter.onClickSelectInputCurrency()
            }

            R.id.imageButton_select_output_currency -> {
                presenter.onClickSelectOutputCurrency()
            }
        }
    }

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
        adapter = CurrencyAdapter()

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
        val currencyInputView = findViewById<EditText>(R.id.editText_currency_input)
        currencyInputView.addTextChangedListener(object : TextWatcher {
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
                val inputValue = currencyInputView.text.toString()
                presenter.onCurrencyTextChanged(inputValue)
            }
        })

        //View
        findViewById<ImageButton>(R.id.imageButton_select_input_currency).setOnClickListener(this)
        findViewById<ImageButton>(R.id.imageButton_select_output_currency).setOnClickListener(this)
    }

    override fun setListCurrency(listValute: List<CurrencyBody>) {
        adapter.setListCurrency(listValute)
    }

    override fun showDialog() {
        currencyListAlertDialog.show()
    }

    override fun hideDialog() {
        currencyListAlertDialog.hide()
    }

    override fun setCurrencyValue(currencyValue: String) {
        val currencyOutputView = findViewById<TextView>(R.id.textView_currency_output)
        currencyOutputView.text = currencyValue
    }

    override fun setInputCurrencyCharCode(charCode: String) {
        val inputCharCodeView = findViewById<TextView>(R.id.textView_input_currency_char_code)
        inputCharCodeView.text = charCode
    }

    override fun setOutputCurrencyCharCode(charCode: String) {
        val outputCharCodeView = findViewById<TextView>(R.id.textView_output_currency_char_code)
        outputCharCodeView.text = charCode
    }
}
