package com.fwouts.buyrent.repositories

import com.fwouts.buyrent.api.*
import com.fwouts.buyrent.domain.Property
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemotePropertyRepository @Inject constructor(val api: BuyRentApi) : PropertyRepository {
    override fun getList(): Flow<List<Property>> {
        return flow {
            val result = api.search(
                SearchRequest(
                    dwelling_types = listOf(DwellingType.APARTMENT),
                    search_mode = SearchMode.BUY
                )
            )
            emit(
                result.search_results.filterIsInstance<PropertyListing>().map { listing ->
                    with(listing) {
                        Property(
                            price = price,
                            address = address,
                            imageUrl = "TODO",
                            agencyLogoUrl = "TODO",
                            bed = listing.bedroom_count,
                            bath = listing.bathroom_count,
                            car = listing.carspace_count
                        )
                    }
                }
            )
        }
    }
}