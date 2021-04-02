package com.example.cft_converter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.databinding.ItemCurrencyBinding
import com.example.cft_converter.domain.entity.CurrencyEntity

class CurrencyAdapter(
    private val onClickItem: (Int) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    var listOfCurrency: List<CurrencyEntity> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemCurrency = ItemCurrencyBinding.inflate(layoutInflater, parent, false)
        return CurrencyViewHolder(itemCurrency)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(listOfCurrency[position])
        holder.itemView.setOnClickListener {
            onClickItem(position)
        }
    }

    override fun getItemCount(): Int = listOfCurrency.size
}
