package com.fwouts.buyrent.repositories.remote

import androidx.paging.*
import com.fwouts.buyrent.api.*
import com.fwouts.buyrent.domain.Property
import com.fwouts.buyrent.repositories.ListType
import com.fwouts.buyrent.repositories.PropertyRepository
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
                PropertyListPagingSource(
                    api, SearchRequest(
                        dwelling_types = listOf(DwellingType.APARTMENT),
                        search_mode = type.let {
                            when (it) {
                                ListType.BUY -> SearchMode.BUY
                                ListType.RENT -> SearchMode.RENT
                            }
                        })
                )
            }
        )
        return pager.flow.map { pagingData ->
            pagingData.filter { it is PropertyListing }.map { (it as PropertyListing).toProperty() }
        }
    }
}
