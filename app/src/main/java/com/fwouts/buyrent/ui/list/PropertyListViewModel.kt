package com.fwouts.buyrent.ui.list

import androidx.lifecycle.*
import com.fwouts.buyrent.domain.Property
import com.fwouts.buyrent.repositories.PropertyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(val repository: PropertyRepository) : ViewModel() {
    private val _list = repository.getList().asLiveData()

    val list: LiveData<List<PropertyViewModel>> = Transformations.map(_list) { it ->
        it.map { property -> PropertyViewModel(property) }
    }
}