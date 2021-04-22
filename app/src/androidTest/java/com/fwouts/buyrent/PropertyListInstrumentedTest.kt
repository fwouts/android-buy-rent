package com.fwouts.buyrent

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.fwouts.buyrent.api.*
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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

    @BindValue
    @MockK
    lateinit var mockApi: BuyRentApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun showsEmptyMessageWhenNoResults() {
        coEvery { mockApi.search(eq(0), any()) }.returns(SearchResponse(emptyList()))
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun showsErrorMessageOnFailure() {
        coEvery { mockApi.search(eq(0), any()) }.throws(RuntimeException())
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.error_view)).check(matches(isDisplayed()))
        onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun showsErrorMessageOnFailureAfterSuccessfulLoad() {
        coEvery { mockApi.search(any(), any()) }.returns(SearchResponse(emptyList()))
        coEvery { mockApi.search(eq(0), any()) }.returns(
            SearchResponse(
                listOf(
                    PROPERTY_LISTING_1,
                    PROPERTY_LISTING_2
                )
            )
        )
        ActivityScenario.launch(MainActivity::class.java)
        onView(withText(PROPERTY_LISTING_1.price)).check(matches(isDisplayed()))
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))

        coEvery { mockApi.search(eq(0), any()) }.throws(RuntimeException())
        onView(withId(R.id.swipe_refresh_container)).perform(swipeDown());

        onView(withId(R.id.error_view)).check(matches(isDisplayed()))
        onView(withText(PROPERTY_LISTING_1.price)).check(matches(not(isDisplayed())))
    }

    @Test
    fun retryButtonRefreshes() {
        coEvery { mockApi.search(eq(0), any()) }.throws(RuntimeException())
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.error_view)).check(matches(isDisplayed()))

        coEvery { mockApi.search(eq(0), any()) }.returns(SearchResponse(emptyList()))
        onView(withId(R.id.retry_button)).perform(click())
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun showsPaginatedResults() {
        coEvery { mockApi.search(any(), any()) }.returns(SearchResponse(emptyList()))
        coEvery { mockApi.search(eq(0), any()) }.returns(
            SearchResponse(
                listOf(
                    PROPERTY_LISTING_1
                )
            )
        )
        coEvery { mockApi.search(eq(1), any()) }.returns(
            SearchResponse(
                listOf(
                    PROPERTY_LISTING_2
                )
            )
        )
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))

        // First property.
        onView(withText("not for sale")).check(matches(isDisplayed()))
        onView(withText("1 Infinity Loop")).check(matches(isDisplayed()))
        onView(withText("2.5 bed, 2 bath, 1 car")).check(matches(isDisplayed()))

        // Second property.
        onView(withText("$20M")).check(matches(isDisplayed()))
        onView(withText("100 Harris Street, Pyrmont")).check(matches(isDisplayed()))
        onView(withText("0 bed, 12 bath")).check(matches(isDisplayed()))
    }

    @Test
    fun showsExpectedContentForEachTab() {
        val buyPropertyLabel = "to buy"
        val rentPropertyLabel = "for rent"

        coEvery { mockApi.search(any(), any()) }.returns(SearchResponse(emptyList()))
        coEvery { mockApi.search(eq(0), any()) }.coAnswers { call ->
            val request = call.invocation.args[1] as SearchRequest
            return@coAnswers SearchResponse(
                when (request.search_mode) {
                    SearchMode.BUY -> listOf(
                        makePropertyListing(id = 1, price = buyPropertyLabel)
                    )
                    SearchMode.RENT -> listOf(
                        makePropertyListing(id = 1, price = rentPropertyLabel)
                    )
                }
            )
        }
        ActivityScenario.launch(MainActivity::class.java)

        // By default, the Buy tab should be selected.
        onView(withText(buyPropertyLabel)).check(matches(isDisplayed()))
        onView(withText(rentPropertyLabel)).check(doesNotExist())

        onView(withText("Rent")).perform(click())
        onView(withText(buyPropertyLabel)).check(doesNotExist())
        onView(withText(rentPropertyLabel)).check(matches(isDisplayed()))

        onView(withText("Buy")).perform(click())
        onView(withText(buyPropertyLabel)).check(matches(isDisplayed()))
        onView(withText(rentPropertyLabel)).check(doesNotExist())
    }
}