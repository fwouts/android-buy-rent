package com.fwouts.buyrent.api

import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BuyRentApi {
    @POST("search")
    suspend fun search(@Body request: SearchRequest): SearchResult
}