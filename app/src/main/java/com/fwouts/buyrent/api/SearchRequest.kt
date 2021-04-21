package com.fwouts.buyrent.api

import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

@JsonClass(generateAdapter = true)
data class SearchRequest(
    val dwelling_types: List<DwellingType>,
    val search_mode: SearchMode
)

enum class DwellingType(val label: String) {
    APARTMENT("Apartment / Unit / Flat");
}

class DwellingTypeAdapter {
    @ToJson
    fun toJson(type: DwellingType): String {
        return type.label
    }
}

enum class SearchMode(val key: String) {
    BUY("buy"),
    RENT("rent")
}

class SearchModeAdapter {
    @ToJson
    fun toJson(mode: SearchMode): String {
        return mode.key
    }
}