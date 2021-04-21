package com.fwouts.buyrent.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import com.fwouts.buyrent.R
import com.fwouts.buyrent.domain.Property

class PropertyCardViewModelImpl(
    private val application: Application,
    private val property: Property
) :
    ViewModel(), PropertyCardViewModel {
    override val id: Long
        get() {
            return property.id
        }

    override val price: String
        get() {
            return property.price
        }

    override val description: String
        get() {
            return property.car?.let { car ->
                application.resources.getString(
                    R.string.property_card_description_with_car,
                    this.formatOptionalDecimal(property.bed),
                    this.formatOptionalDecimal(property.bath),
                    car.toString(10)
                )
            } ?: application.resources.getString(
                R.string.property_card_description_without_car,
                this.formatOptionalDecimal(property.bed),
                this.formatOptionalDecimal(property.bath)
            )
        }

    override val address: String
        get() {
            return property.address
        }

    override val imageUrl: String
        get() {
            // TODO: Introduce a reasonable fallback if it's ever possible to have an empty list.
            return property.imageUrls[0]
        }

    override val agencyLogoUrl: String
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
