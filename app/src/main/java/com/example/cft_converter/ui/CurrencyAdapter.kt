package com.example.cft_converter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.domain.entity.CurrencyBody

class CurrencyAdapter(
    private val onClickItemListener: (Int) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    var listOfCurrencies: List<CurrencyBody> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(listOfCurrencies[position])

        holder.itemView.setOnClickListener {
            onClickItemListener(position)
        }
    }

    override fun getItemCount(): Int = listOfCurrencies.size
}
