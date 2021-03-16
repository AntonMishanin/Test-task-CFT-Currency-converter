package com.example.cft_converter.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.domain.entity.CurrencyBody

class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var currencyTitleView: TextView? = null

    init {
        currencyTitleView = itemView.findViewById(R.id.textView_item_currency_title)
    }

    fun bind(currency: CurrencyBody) {
        currencyTitleView?.text = currency.Name
    }
}