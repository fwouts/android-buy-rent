package com.fwouts.buyrent.domain

import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class Property(
    val id: Long,
    val price: String,
    val address: String,
    val imageUrl: String,
    val agencyLogoUrl: String,
    val bed: Float,
    val bath: Float,
    val car: Int?
)
