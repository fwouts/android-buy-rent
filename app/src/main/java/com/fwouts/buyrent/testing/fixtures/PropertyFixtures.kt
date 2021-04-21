package com.fwouts.buyrent.testing.fixtures

import com.fwouts.buyrent.domain.Property

class PropertyFixtures {
    companion object {
        val PROPERTY_1 = Property(
            id = 1,
            price = "Contact Agent",
            address = "51 Imperial Avenue, Bondi",
            bed = 4.0f,
            bath = 2.0f,
            car = 4,
            imageUrl = "",
            agencyLogoUrl = ""
        )
        val PROPERTY_2 = Property(
            id = 2,
            price = "Auction",
            address = "2/285-295 Bondi Road, Bondi",
            bed = 3.0f,
            bath = 2.0f,
            car = 2,
            imageUrl = "",
            agencyLogoUrl = ""
        )
        val PROPERTY_3 = Property(
            id = 3,
            price = "AUCTION",
            address = "4/30 Penkivil Street, Bondi",
            bed = 3.0f,
            bath = 2.0f,
            car = 1,
            imageUrl = "",
            agencyLogoUrl = ""
        )

        val PROPERTIES = listOf(PROPERTY_1, PROPERTY_2, PROPERTY_3)
    }
}