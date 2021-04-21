package com.fwouts.buyrent.domain

import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
data class Property(
    val price: String,
    val address: String,
    val imageUrl: String,
    val agencyLogoUrl: String,
    val bed: Int,
    val bath: Int,
    val car: Int
)
