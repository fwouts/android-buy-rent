package com.fwouts.buyrent.ui.list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.map
import com.fwouts.buyrent.domain.Property
import com.fwouts.buyrent.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(val repository: PropertyRepository) : ViewModel() {
    private val _list = repository.getList()

    val list: Flow<PagingData<PropertyViewModel>> = _list.map { pagingData ->
        pagingData.map {  property -> PropertyViewModel(property)}
    }
}