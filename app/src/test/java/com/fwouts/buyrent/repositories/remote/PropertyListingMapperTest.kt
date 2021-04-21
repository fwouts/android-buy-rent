package com.fwouts.buyrent.repositories.remote

import com.fwouts.buyrent.api.Advertiser
import com.fwouts.buyrent.api.AdvertiserImages
import com.fwouts.buyrent.api.Media
import com.fwouts.buyrent.api.PropertyListing
import com.fwouts.buyrent.domain.Property
import io.kotest.matchers.shouldBe
import org.junit.Test

class PropertyListingMapperTest {
    @Test
    fun `converts as expected`() {
        val listing = PropertyListing(
            id = 123,
            price = "a price",
            address = "an address",
            bedroom_count = 2.5f,
            bathroom_count = 2.0f,
            carspace_count = 2,
            media = listOf(Media("media_1"),Media("media_2")),
            advertiser = Advertiser(
                images = AdvertiserImages(
                    logo_url = "advertiser_logo"
                )
            )
        )

        listing.toProperty() shouldBe Property(
            id = 123,
            price = "a price",
            address = "an address",
            bed = 2.5f,
            bath = 2.0f,
            car = 2,
            imageUrls = listOf("media_1", "media_2"),
            agencyLogoUrl = "advertiser_logo"
        )
    }
}