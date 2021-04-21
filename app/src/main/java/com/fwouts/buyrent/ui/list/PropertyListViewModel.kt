package com.fwouts.buyrent.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.fwouts.buyrent.repositories.ListType
import com.fwouts.buyrent.repositories.PropertyRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PropertyListViewModel @AssistedInject constructor(
    repository: PropertyRepository,
    private val application: Application,
    @Assisted type: ListType
) : ViewModel() {
    private val _list = repository.getList(type)

    val list: Flow<PagingData<PropertyViewModel>> = _list.map { pagingData ->
        pagingData.map { property -> PropertyViewModel(application, property) }
    }

    @AssistedFactory
    interface Factory {
        fun create(type: ListType): PropertyListViewModel
    }
}