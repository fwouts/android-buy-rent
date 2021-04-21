package com.fwouts.buyrent.api

import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

@JsonClass(generateAdapter = true)
data class SearchResponse(
    val search_results: List<SearchListing>
)

interface SearchListing {
    val id: Long
    val address: String
    val media: List<Media>
}

@JsonClass(generateAdapter = true)
data class PropertyListing(
    override val id: Long,
    override val address: String,
    override val media: List<Media>,
    val advertiser: Advertiser,
    val price: String,
    val bedroom_count: Float,
    val bathroom_count: Float,
    val carspace_count: Int?,
): SearchListing

@JsonClass(generateAdapter = true)
data class TopSpotListing(
    override val id: Long,
    override val address: String,
    override val media: List<Media>,
): SearchListing


@JsonClass(generateAdapter = true)
data class ProjectListing(
    override val id: Long,
    override val address: String,
    override val media: List<Media>,
): SearchListing

// TODO: Do not crash when encountering media other than images.
@JsonClass(generateAdapter = true)
data class Media(
    val image_url: String
)

@JsonClass(generateAdapter = true)
data class Advertiser(
    val images: AdvertiserImages
)

@JsonClass(generateAdapter = true)
data class AdvertiserImages(
    val logo_url: String
)