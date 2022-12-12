package com.melyseev.factnumber.presentation.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.melyseev.factnumber.R
import com.melyseev.factnumber.domain.NumberFact
import com.melyseev.factnumber.presentation.Change
import com.melyseev.factnumber.presentation.Mutable
import com.melyseev.factnumber.presentation.NumberUI

class NumbersAdapter: RecyclerView.Adapter<NumbersAdapter.NumbersViewHolder>(), Change<Unit, List<NumberUI>> {


    val listNumbers = mutableListOf<NumberUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
       val view =  LayoutInflater.from(parent.context).inflate(R.layout.description_number, parent, false)
       return NumbersViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        val numberUI = listNumbers[position]
        holder.number.text = numberUI.id
        holder.number_desc.text = numberUI.fact
    }

    override fun getItemCount(): Int  = listNumbers.size


    class NumbersViewHolder(itemView: View) : ViewHolder(itemView) {
        val number          = itemView.findViewById<TextView>(R.id.number_only)
        val number_desc     = itemView.findViewById<TextView>(R.id.number_desc)
    }

    override fun change(source: List<NumberUI>) {
        listNumbers.clear()
        listNumbers.addAll(source)
        notifyDataSetChanged()
    }

}