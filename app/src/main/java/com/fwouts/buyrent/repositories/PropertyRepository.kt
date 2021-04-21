package com.fwouts.buyrent.repositories

import com.fwouts.buyrent.domain.Property
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {
    fun getList(): Flow<List<Property>>
}