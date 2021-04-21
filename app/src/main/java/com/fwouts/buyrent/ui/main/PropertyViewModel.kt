package com.fwouts.buyrent.ui.main

import androidx.lifecycle.ViewModel
import com.fwouts.buyrent.domain.Property

class PropertyViewModel(val property: Property) : ViewModel() {

    val price: String
        get() {
            return property.price
        }
}
