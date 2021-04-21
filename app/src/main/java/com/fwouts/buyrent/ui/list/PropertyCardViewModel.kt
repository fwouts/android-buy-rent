package com.fwouts.buyrent.ui.list

import androidx.recyclerview.widget.DiffUtil

interface PropertyCardViewModel {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PropertyCardViewModel>() {
            override fun areItemsTheSame(
                oldItem: PropertyCardViewModel,
                newItem: PropertyCardViewModel
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PropertyCardViewModel,
                newItem: PropertyCardViewModel
            ) = (
                    oldItem.price == newItem.price &&
                            oldItem.description == newItem.description &&
                            oldItem.address == newItem.address &&
                            oldItem.imageUrl == newItem.imageUrl &&
                            oldItem.agencyLogoUrl == newItem.agencyLogoUrl
                    )
        }
    }

    val id: Long
    val price: String
    val description: String
    val address: String
    val imageUrl: String
    val agencyLogoUrl: String
}
