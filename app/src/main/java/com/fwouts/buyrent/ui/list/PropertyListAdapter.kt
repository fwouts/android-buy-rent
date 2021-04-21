package com.fwouts.buyrent.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fwouts.buyrent.R

class PropertyListAdapter :
    PagingDataAdapter<PropertyViewModel, PropertyListAdapter.ViewHolder>(PropertyViewModel.COMPARATOR) {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView = view.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_property_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { viewModel ->
            with(viewModel) {
                holder.price.text = price
            }
        }
    }
}