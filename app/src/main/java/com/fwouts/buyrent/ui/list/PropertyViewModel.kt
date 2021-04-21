package com.fwouts.buyrent.ui.list

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import com.fwouts.buyrent.domain.Property

class PropertyViewModel(val property: Property) : ViewModel() {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PropertyViewModel>() {
            override fun areItemsTheSame(oldItem: PropertyViewModel, newItem: PropertyViewModel): Boolean =
                oldItem.id == newItem.id

            // TODO: Ensure all properties are covered.
            override fun areContentsTheSame(oldItem: PropertyViewModel, newItem: PropertyViewModel): Boolean =
                oldItem.price == newItem.price
        }
    }

    val id: Long
        get() {
            return property.id
        }

    val price: String
        get() {
            return property.price
        }
}
