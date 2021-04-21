package com.fwouts.buyrent.repositories

import androidx.paging.*
import com.fwouts.buyrent.api.*
import com.fwouts.buyrent.domain.Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemotePropertyRepository @Inject constructor(val api: BuyRentApi) : PropertyRepository {
    override fun getList(type: ListType): Flow<PagingData<Property>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                // TODO: Actually use pagination in the API request.
                PropertyListPagingSource(api) { page ->
                    SearchRequest(
                        dwelling_types = listOf(DwellingType.APARTMENT),
                        search_mode = type.let {
                            when (it) {
                                ListType.BUY -> SearchMode.BUY
                                ListType.RENT -> SearchMode.RENT
                            }
                        }
                    )
                }
            }
        )
        return pager.flow.map { pagingData ->
            pagingData.filter { it is PropertyListing }.map { listing ->
                with(listing as PropertyListing) {
                    Property(
                        id = id,
                        price = price,
                        address = address,
                        imageUrls = listing.media.map { it.image_url },
                        agencyLogoUrl = listing.advertiser.images.logo_url,
                        bed = listing.bedroom_count,
                        bath = listing.bathroom_count,
                        car = listing.carspace_count
                    )
                }
            }
        }
    }
}
