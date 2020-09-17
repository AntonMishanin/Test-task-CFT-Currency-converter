package com.example.cft_converter.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.data.network.RetrofitService
import com.example.cft_converter.domain.CurrencyUseCase

class CurrencyActivity : AppCompatActivity(), CurrencyView, View.OnClickListener {

    private val retrofit = RetrofitService()
    private val api = retrofit.provideCurrencyApi(retrofit.provideRetrofit())
    private val useCase = CurrencyUseCase()
    private val presenter = CurrencyPresenter(this, api, useCase)

    private lateinit var orderAlertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        presenter.onViewCreated()
    }

    override fun onClick(v: View?) {

    }

    override fun initView() {
//Dialog
        orderAlertDialog = AlertDialog.Builder(
            this,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar
        ).create()

        val listOrderDialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_order_detail_list_goods, null, false)
        orderAlertDialog.setView(listOrderDialogView)

        //RecyclerView
        val recyclerViewOrderDetail =
            findViewById<RecyclerView>(R.id.recyclerView_detail_orders)
        recyclerViewOrderDetail?.layoutManager = LinearLayoutManager(this)
        recyclerViewOrderDetail?.adapter = detailAdapter

        detailAdapter.setListener(object : ItemClickListener {
            override fun onItemClick(position: Int) {
                presenter.onItemOrderClick(position)
            }
        })

        //TextChangedListener

        numberView.addTextChangedListener(object : TextWatcher {

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
                val inputText = numberView.text.toString()
                presenter.onItemOrderNumberChanged(inputText)
            }
        })
    }

    override fun setListCurrency() {

    }

    override fun showDialog() {

    }

    override fun hideDialog() {

    }

    override fun setCurrencyValue(currencyValue: String) {

    }


}
