package com.fwouts.buyrent.ui.list

import androidx.test.core.app.ApplicationProvider
import com.fwouts.buyrent.domain.Property
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PropertyCardViewModelImplTest {
    @Test
    fun `exposes the expected values`() {
        val vm = PropertyCardViewModelImpl(
            ApplicationProvider.getApplicationContext(), Property(
                id = 123,
                price = "some price",
                address = "an address",
                imageUrls = listOf("image_1", "image_2"),
                agencyLogoUrl = "logo_1",
                bed = 2.5f,
                bath = 2f,
                car = 3,
            )
        )
        vm.id shouldBe 123
        vm.price shouldBe "some price"
        vm.address shouldBe "an address"
        vm.imageUrl shouldBe "image_1"
        vm.agencyLogoUrl shouldBe "logo_1"
        vm.description shouldBe "2.5 bed, 2 bath, 3 car"
    }

    @Test
    fun `omits car count when missing`() {
        val vm = PropertyCardViewModelImpl(
            ApplicationProvider.getApplicationContext(), Property(
                id = 123,
                price = "some price",
                address = "an address",
                imageUrls = listOf("image_1", "image_2"),
                agencyLogoUrl = "logo_1",
                bed = 2.5f,
                bath = 2f,
                car = null,
            )
        )
        vm.description shouldBe "2.5 bed, 2 bath"
    }
}