package com.fwouts.buyrent

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.fwouts.buyrent.api.*
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever

@UninstallModules(ApiModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class PropertyListInstrumentedTest {
    companion object {
        val PROPERTY_LISTING_1 = makePropertyListing(
            id = 2,
            price = "$20M",
            address = "100 Harris Street, Pyrmont",
            bedroom_count = 0.0f,
            bathroom_count = 12.0f,
            carspace_count = null,
        )
        val PROPERTY_LISTING_2 = makePropertyListing(
            id = 1,
            price = "not for sale",
            address = "1 Infinity Loop",
            bedroom_count = 2.5f,
            bathroom_count = 2.0f,
            carspace_count = 1,
        )

        fun makePropertyListing(
            id: Long,
            price: String,
            address: String? = null,
            bedroom_count: Float? = null,
            bathroom_count: Float? = null,
            carspace_count: Int? = null
        ) = PropertyListing(
            id = id,
            price = price,
            address = address ?: "address",
            bedroom_count = bedroom_count ?: 2.5f,
            bathroom_count = bathroom_count ?: 2.0f,
            carspace_count = carspace_count,
            media = listOf(Media("foo")),
            advertiser = Advertiser(
                images = AdvertiserImages(
                    logo_url = "foo"
                )
            )
        )
    }

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val mockitoRule = MockitoJUnit.testRule(this)

    @BindValue
    @Mock
    lateinit var mockApi: BuyRentApi

    @Test
    fun showsEmptyMessageWhenNoResults() {
        runBlocking {
            whenever(mockApi.search(eq(0), any())).thenReturn(SearchResponse(emptyList()))
        }
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
    }

    @Test
    fun showsPaginatedResults() {
        runBlocking {
            whenever(mockApi.search(any(), any())).thenReturn(SearchResponse(emptyList()))
            whenever(mockApi.search(eq(0), any())).thenReturn(
                SearchResponse(
                    listOf(
                        PROPERTY_LISTING_1
                    )
                )
            )
            whenever(mockApi.search(eq(1), any())).thenReturn(
                SearchResponse(
                    listOf(
                        PROPERTY_LISTING_2
                    )
                )
            )
        }
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))

        // First property.
        onView(withText("not for sale")).check(matches(isDisplayed()))
        onView(withText("1 Infinity Loop")).check(matches(isDisplayed()))
        onView(withText("2.5 bed, 2 bath, 1 car")).check(matches(isDisplayed()))

        // Second property.
        onView(withText("$20M")).check(matches(isDisplayed()))
        onView(withText("100 Harris Street, Pyrmont")).check(matches(isDisplayed()))
        onView(withText("0 bed, 12 bath")).check(matches(isDisplayed()))
    }

    // TODO: Test switching between tabs.
}