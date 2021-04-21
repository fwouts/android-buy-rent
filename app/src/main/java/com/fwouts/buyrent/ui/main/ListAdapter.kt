package com.fwouts.buyrent.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fwouts.buyrent.R

class ListAdapter() : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView = view.findViewById(R.id.price)
    }

    var list: List<PropertyViewModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_property_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.price.text = item.price
    }

    override fun getItemCount(): Int {
        return list.size
    }
}