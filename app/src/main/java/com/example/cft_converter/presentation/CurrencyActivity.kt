package com.example.cft_converter.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.data.CurrencyRepositoryImpl
import com.example.cft_converter.data.database.RealmDb
import com.example.cft_converter.data.network.CurrencyRemoteDataSource
import com.example.cft_converter.data.network.RetrofitService
import com.example.cft_converter.domain.CurrencyUseCase
import com.example.cft_converter.domain.ICurrencyRepository
import com.example.cft_converter.domain.entity.CurrencyBody
import io.realm.Realm
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

/**
 * Мишанин Антон
 * https://github.com/AntonMishanin/Test-task-CFT-Currency-converter
 */

class CurrencyActivity : MvpAppCompatActivity(), CurrencyView, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: CurrencyPresenter

    private lateinit var currencyListAlertDialog: AlertDialog
    private var adapter = CurrencyAdapter()

    private val retrofit = RetrofitService()
    private val api = retrofit.provideCurrencyApi(retrofit.provideRetrofit())
    private val network = CurrencyRemoteDataSource(api)

    private val realm: Realm = Realm.getDefaultInstance()
    private val realmDb = RealmDb(realm)

    private val repository: ICurrencyRepository = CurrencyRepositoryImpl(realmDb, network)
    private val useCase = CurrencyUseCase(repository)

    private var outputCharCodeView: TextView? = null
    private var inputCharCodeView: TextView? = null
    private var outputCurrencyView: EditText? = null
    private var inputCurrencyView: EditText? = null

    @ProvidePresenter
    fun providePresenter() = CurrencyPresenter(useCase)

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
        outputCharCodeView = findViewById(R.id.textView_output_currency_char_code)
        inputCharCodeView = findViewById(R.id.textView_input_currency_char_code)

        //Dialog
        currencyListAlertDialog = AlertDialog.Builder(
            this,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ).create()

        val currencyListView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_list_currency, null, false)
        currencyListAlertDialog.setView(currencyListView)

        currencyListAlertDialog.setOnCancelListener {
            presenter.hideDialog()
        }

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
        inputCurrencyView = findViewById(R.id.editText_currency_input)
        inputCurrencyView?.addTextChangedListener(object : TextWatcher {
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
                presenter.onInputCurrencyTextChanged(s)
            }
        })

        outputCurrencyView = findViewById(R.id.textView_currency_output)
        outputCurrencyView?.addTextChangedListener(object : TextWatcher {
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
                presenter.onOutputCurrencyTextChanged(s)
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
        currencyListAlertDialog.dismiss()
    }

    override fun setInputCurrencyValue(currencyValue: String) {
        runOnUiThread {
            inputCurrencyView?.setText(currencyValue)
        }
    }

    override fun setOutputCurrencyValue(currencyValue: String) {
        runOnUiThread {
            outputCurrencyView?.setText(currencyValue)
        }
    }

    override fun setInputCurrencyCharCode(charCode: String) {
        runOnUiThread {
            inputCharCodeView?.text = charCode
        }
    }

    override fun setOutputCurrencyCharCode(charCode: String) {
        runOnUiThread {
            outputCharCodeView?.text = charCode
        }
    }

    override fun showListLoadingError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}
