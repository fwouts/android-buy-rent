package com.fwouts.buyrent.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.DiffUtil
import com.fwouts.buyrent.R
import com.fwouts.buyrent.domain.Property

interface PropertyCardViewModel {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PropertyCardViewModel>() {
            override fun areItemsTheSame(
                oldItem: PropertyCardViewModel,
                newItem: PropertyCardViewModel
            ): Boolean =
                oldItem.id == newItem.id

            // TODO: Ensure all properties are covered.
            override fun areContentsTheSame(
                oldItem: PropertyCardViewModel,
                newItem: PropertyCardViewModel
            ): Boolean =
                oldItem.price == newItem.price
        }
    }

    val id: Long
    val price: String
    val description: String
    val address: String
    val imageUrl: String
    val agencyLogoUrl: String
}
