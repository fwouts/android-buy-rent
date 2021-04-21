package com.fwouts.buyrent.repositories

import androidx.paging.PagingData
import com.fwouts.buyrent.domain.Property
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {
    fun getList(type: ListType): Flow<PagingData<Property>>
}

enum class ListType {
    BUY,
    RENT
}