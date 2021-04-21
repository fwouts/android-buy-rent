package com.fwouts.buyrent.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.DiffUtil
import com.fwouts.buyrent.R
import com.fwouts.buyrent.domain.Property

class PropertyCardViewModel(application: Application, private val property: Property) :
    AndroidViewModel(application) {
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
                    this.formatOptionalDecimal(property.bed),
                    this.formatOptionalDecimal(property.bath),
                    car.toString(10)
                )
            } ?: resources.getString(
                R.string.property_card_description_without_car,
                this.formatOptionalDecimal(property.bed),
                this.formatOptionalDecimal(property.bath)
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

    /**
     * Returns a decimal representation when there is a decimal part, otherwise returns an integer
     * representation.
     *
     * See https://stackoverflow.com/questions/703396
     *
     * TODO: There is probably a much better way to do this. Investigate when there is more time.
     */
    private fun formatOptionalDecimal(d: Float): String {
        return if (d % 1.0 == 0.0) {
            String.format(
                "%d",
                d.toLong()
            )
        } else {
            String.format("%.1f", d)
        }
    }
}