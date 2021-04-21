package com.fwouts.buyrent.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fwouts.buyrent.domain.Property

class ListViewModel : ViewModel() {

    private val _list = MutableLiveData<List<Property>>()

    val list: LiveData<List<PropertyViewModel>> = Transformations.map(_list) { it ->
        it.map { property -> PropertyViewModel(property) }
    }

    fun setList(list: List<Property>) {
        _list.value = list
    }
}