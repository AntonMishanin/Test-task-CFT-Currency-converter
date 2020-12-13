package com.example.cft_converter.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R
import com.example.cft_converter.domain.entity.CurrencyBody

class CurrencyAdapter(private val onItemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var listValute: List<CurrencyBody> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listValute[position])

        holder.itemView.setOnClickListener {
            onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int = listValute.size

    fun setListCurrency(listValute: List<CurrencyBody>) {
        this.listValute = listValute
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var currencyTitleView: TextView? = null

        init {
            currencyTitleView = itemView.findViewById(R.id.textView_item_currency_title)
        }

        fun bind(currency: CurrencyBody) {
            currencyTitleView?.text = currency.Name
        }
    }
}
