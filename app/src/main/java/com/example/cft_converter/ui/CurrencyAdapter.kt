package com.example.cft_converter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.domain.entity.CurrencyEntity

class CurrencyAdapter(
    private val onClickItemListener: (Int) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    var listOfCurrencyEntities: List<CurrencyEntity> = ArrayList()
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
        holder.bind(listOfCurrencyEntities[position])

        holder.itemView.setOnClickListener {
            onClickItemListener(position)
        }
    }

    override fun getItemCount(): Int = listOfCurrencyEntities.size
}
