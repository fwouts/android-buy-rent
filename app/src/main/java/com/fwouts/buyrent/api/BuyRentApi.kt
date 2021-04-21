package com.fwouts.buyrent.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface BuyRentApi {
    // Note: The "page" query parameter doesn't actually exist, as far as I know. It's useful for
    // test purposes however.
    @POST("search")
    suspend fun search(@Query("page") page: Int, @Body request: SearchRequest): SearchResponse
}