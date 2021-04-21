package com.fwouts.buyrent.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fwouts.buyrent.api.BuyRentApi
import com.fwouts.buyrent.api.SearchListing
import com.fwouts.buyrent.api.SearchRequest
import retrofit2.HttpException
import java.io.IOException

class PropertyListPagingSource(
    private val api: BuyRentApi,
    private val request: (page: Int) -> SearchRequest
) : PagingSource<Int, SearchListing>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchListing> {
        val page = params.key ?: 0
        return try {
            val result = api.search(request(page))
            return LoadResult.Page(
                data = result.search_results,
                nextKey = page + 1,
                prevKey = if (page > 0) {
                    page - 1
                } else {
                    null
                }
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchListing>): Int? {
        return null
    }
}