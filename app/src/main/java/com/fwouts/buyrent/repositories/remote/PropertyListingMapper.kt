package com.fwouts.buyrent.repositories.remote

import com.fwouts.buyrent.api.PropertyListing
import com.fwouts.buyrent.domain.Property

fun PropertyListing.toProperty() = Property(
    id = id,
    price = price,
    address = address,
    imageUrls = media.map { it.image_url },
    agencyLogoUrl = advertiser.images.logo_url,
    bed = bedroom_count,
    bath = bathroom_count,
    car = carspace_count
)
