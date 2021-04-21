package com.fwouts.buyrent.ui.list

import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class PropertyCardViewModelTest {
    @RelaxedMockK
    lateinit var mockCardA: PropertyCardViewModel

    @RelaxedMockK
    lateinit var mockCardB: PropertyCardViewModel

    @RelaxedMockK
    lateinit var mockCardC: PropertyCardViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `comparator should tell that items are the same when they have the same ID`() {
        every { mockCardA.id }.returns(1)
        every { mockCardB.id }.returns(1)
        every { mockCardC.id }.returns(1)
        PropertyCardViewModel.COMPARATOR.areItemsTheSame(mockCardA, mockCardB) shouldBe true
        PropertyCardViewModel.COMPARATOR.areItemsTheSame(mockCardA, mockCardC) shouldBe true
    }

    @Test
    fun `comparator checks price`() {
        every { mockCardA.price }.returns("$5")
        every { mockCardB.price }.returns("$5")
        every { mockCardC.price }.returns("$10")
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardB) shouldBe true
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardC) shouldBe false
    }

    @Test
    fun `comparator checks description`() {
        every { mockCardA.description }.returns("a")
        every { mockCardB.description }.returns("a")
        every { mockCardC.description }.returns("b")
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardB) shouldBe true
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardC) shouldBe false
    }

    @Test
    fun `comparator checks address`() {
        every { mockCardA.address }.returns("1 Some Street")
        every { mockCardB.address }.returns("1 Some Street")
        every { mockCardC.address }.returns("2 Some Other Street")
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardB) shouldBe true
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardC) shouldBe false
    }

    @Test
    fun `comparator checks image URL`() {
        every { mockCardA.imageUrl }.returns("image_1")
        every { mockCardB.imageUrl }.returns("image_1")
        every { mockCardC.imageUrl }.returns("image_2")
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardB) shouldBe true
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardC) shouldBe false
    }

    @Test
    fun `comparator checks agency logo URL`() {
        every { mockCardA.agencyLogoUrl }.returns("image_1")
        every { mockCardB.agencyLogoUrl }.returns("image_1")
        every { mockCardC.agencyLogoUrl }.returns("image_2")
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardB) shouldBe true
        PropertyCardViewModel.COMPARATOR.areContentsTheSame(mockCardA, mockCardC) shouldBe false
    }
}