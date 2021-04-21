package com.fwouts.buyrent.ui.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

class PropertyListAdapter :
    PagingDataAdapter<PropertyCardViewModel, PropertyCardViewHolder>(PropertyCardViewModel.COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyCardViewHolder {
        return PropertyCardViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PropertyCardViewHolder, position: Int) {
        getItem(position)?.let { viewModel ->
            holder.bind(viewModel)
        }
    }

    override fun onViewRecycled(holder: PropertyCardViewHolder) {
        holder.onRecycled()
    }
}