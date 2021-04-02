package com.example.cft_converter.ui

import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.databinding.ItemCurrencyBinding
import com.example.cft_converter.domain.entity.CurrencyEntity

class CurrencyViewHolder(private val itemCurrency: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(itemCurrency.root) {

    fun bind(currencyEntity: CurrencyEntity) {
        itemCurrency.title.text = currencyEntity.name
    }
}