package com.fwouts.buyrent.testing.fixtures

import com.fwouts.buyrent.domain.Property

class PropertyFixtures {
    companion object {
        val PROPERTY_1 = Property(
            price = "Contact Agent",
            address = "51 Imperial Avenue, Bondi",
            bed = 4,
            bath = 2,
            car = 4,
            imageUrl = "",
            agencyLogoUrl = ""
        )
        val PROPERTY_2 = Property(
            price = "Auction",
            address = "2/285-295 Bondi Road, Bondi",
            bed = 3,
            bath = 2,
            car = 2,
            imageUrl = "",
            agencyLogoUrl = ""
        )
        val PROPERTY_3 = Property(
            price = "AUCTION",
            address = "4/30 Penkivil Street, Bondi",
            bed = 3,
            bath = 2,
            car = 1,
            imageUrl = "",
            agencyLogoUrl = ""
        )

        val PROPERTIES = listOf(PROPERTY_1, PROPERTY_2, PROPERTY_3)
    }
}