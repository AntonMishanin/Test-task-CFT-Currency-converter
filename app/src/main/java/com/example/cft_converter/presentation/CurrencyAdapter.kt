package com.example.cft_converter.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.domain.entity.CurrencyBody

class CurrencyAdapter  :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var listValute: List<CurrencyBody> = ArrayList()
    private lateinit var listener: CurrencyClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listValute[position], position)
    }

    override fun getItemCount(): Int = listValute.size

    fun setListCurrency(listValute: List<CurrencyBody>) {
        this.listValute = listValute
        notifyDataSetChanged()
    }

    fun setListener(listener: CurrencyClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_currency, parent, false)) {
        private var currencyTitleView: TextView? = null

        init {
            currencyTitleView = itemView.findViewById(R.id.textView_item_currency_title)
        }

        fun bind(currency: CurrencyBody, position: Int) {
            currencyTitleView?.text = currency.Name

            itemView.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }
}

interface CurrencyClickListener {

    fun onItemClick(position: Int)
}
