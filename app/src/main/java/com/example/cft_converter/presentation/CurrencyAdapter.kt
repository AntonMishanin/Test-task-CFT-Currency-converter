package com.example.cft_converter.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cft_converter.R

class CurrencyAdapter  :
    RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var listProfession: MutableList<ProfessionEntityAssets> = ArrayList()
    private lateinit var listener: ProfessionClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listProfession[position], position)
    }

    override fun getItemCount(): Int = listProfession.size

    fun setListProfession(listProfession: List<ProfessionEntityAssets>) {
        this.listProfession.clear()
        this.listProfession.addAll(listProfession)
        notifyDataSetChanged()
    }

    fun setListener(listener: ProfessionClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_profession, parent, false)) {
        private var titleView: TextView? = null

        init {
            titleView = itemView.findViewById(R.id.item_profession_title)
        }

        fun bind(profession: ProfessionEntityAssets, position: Int) {
            titleView?.text = profession.title

            itemView.setOnClickListener {
                listener.onItemClick(position)
            }
        }
    }
}

interface ProfessionClickListener {

    fun onItemClick(position: Int)
}