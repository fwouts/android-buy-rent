package com.fwouts.buyrent.ui.list

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.fwouts.buyrent.repositories.ListType
import com.fwouts.buyrent.repositories.PropertyRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PropertyListViewModel @AssistedInject constructor(
    repository: PropertyRepository,
    private val application: Application,
    @Assisted type: ListType
) : ViewModel() {
    private val _adapter = PropertyListAdapter()
    private val _loadingState = _adapter.loadStateFlow.asLiveData()
    private val _list: Flow<PagingData<PropertyViewModel>> =
        repository.getList(type).map { pagingData ->
            pagingData.map { property -> PropertyViewModel(application, property) }
        }

    init {
        viewModelScope.launch {
            _list.collectLatest {
                _adapter.submitData(it)
            }
        }
    }

    val adapter: RecyclerView.Adapter<PropertyCardViewHolder>
        get() = _adapter

    val refreshing: LiveData<Boolean>
        get() = _loadingState.map { it.refresh is LoadState.Loading }

    val showEmpty: LiveData<Boolean>
        get() = _loadingState.map { it.refresh is LoadState.NotLoading && adapter.itemCount == 0 }

    val showError: LiveData<Boolean>
        get() = _loadingState.map { it.refresh is LoadState.Error && adapter.itemCount == 0 }

    fun refresh() {
        _adapter.refresh()
    }

    @AssistedFactory
    interface Factory {
        fun create(type: ListType): PropertyListViewModel
    }
}