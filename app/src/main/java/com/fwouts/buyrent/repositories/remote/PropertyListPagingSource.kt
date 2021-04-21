package com.fwouts.buyrent.repositories.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fwouts.buyrent.api.BuyRentApi
import com.fwouts.buyrent.api.SearchListing
import com.fwouts.buyrent.api.SearchRequest
import retrofit2.HttpException
import java.io.IOException

class PropertyListPagingSource(
    private val api: BuyRentApi,
    private val request: SearchRequest
) : PagingSource<Int, SearchListing>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchListing> {
        val page = params.key ?: 0
        try {
            val result = api.search(page, request)
            return LoadResult.Page(
                data = result.search_results,
                nextKey = if (result.search_results.isNotEmpty()) {
                    page + 1
                } else {
                    null
                },
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