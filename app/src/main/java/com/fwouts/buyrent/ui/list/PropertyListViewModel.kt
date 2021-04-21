package com.fwouts.buyrent.ui.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.map
import com.fwouts.buyrent.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(
    repository: PropertyRepository,
    private val application: Application
) : ViewModel() {
    private val _list = repository.getList()

    val list: Flow<PagingData<PropertyViewModel>> = _list.map { pagingData ->
        pagingData.map { property -> PropertyViewModel(application, property) }
    }
}