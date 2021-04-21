package com.fwouts.buyrent.api

import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

@JsonClass(generateAdapter = true)
data class SearchResult(
    val search_results: List<SearchListing>
)

interface SearchListing {
    val id: Long
    val address: String
}

@JsonClass(generateAdapter = true)
data class PropertyListing(
    override val id: Long,
    override val address: String,
    val price: String,
    val bedroom_count: Float,
    val bathroom_count: Float,
    val carspace_count: Int?,
): SearchListing

@JsonClass(generateAdapter = true)
data class TopSpotListing(
    override val id: Long,
    override val address: String,
): SearchListing

@JsonClass(generateAdapter = true)
data class ProjectListing(
    override val id: Long,
    override val address: String,
): SearchListing
