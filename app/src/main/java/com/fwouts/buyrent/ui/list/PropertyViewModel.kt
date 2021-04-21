package com.fwouts.buyrent.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.DiffUtil
import com.fwouts.buyrent.R
import com.fwouts.buyrent.domain.Property

class PropertyViewModel(application: Application, private val property: Property) :
    AndroidViewModel(application) {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PropertyViewModel>() {
            override fun areItemsTheSame(
                oldItem: PropertyViewModel,
                newItem: PropertyViewModel
            ): Boolean =
                oldItem.id == newItem.id

            // TODO: Ensure all properties are covered.
            override fun areContentsTheSame(
                oldItem: PropertyViewModel,
                newItem: PropertyViewModel
            ): Boolean =
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

    val description: String
        get() {
            val resources = getApplication<Application>().resources
            return property.car?.let { car ->
                resources.getString(
                    R.string.property_card_description_with_car,
                    property.bed,
                    property.bath,
                    car
                )
            } ?: resources.getString(
                R.string.property_card_description_without_car,
                property.bed,
                property.bath
            )
        }

    val address: String
        get() {
            return property.address
        }

    val imageUrl: String
        get() {
            // TODO: Introduce a reasonable fallback if it's ever possible to have an empty list.
            return property.imageUrls[0]
        }

    val agencyLogoUrl: String
        get() {
            return property.agencyLogoUrl
        }
}
