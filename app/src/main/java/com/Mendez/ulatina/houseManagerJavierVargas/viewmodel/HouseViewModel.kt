package com.Mendez.ulatina.houseManagerJavierVargas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Mendez.ulatina.houseManagerJavierVargas.data.repository.HouseRepository
import com.Mendez.ulatina.houseManagerJavierVargas.model.HouseJavier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseViewModel @Inject constructor(
    private val repository: HouseRepository
): ViewModel() {
    val HouseList : LiveData<List<HouseJavier>> get() = repository.getAll();
    fun insert(house: HouseJavier) = viewModelScope.launch {
        repository.insert(house)
    }
    fun update(house: HouseJavier) = viewModelScope.launch {
        repository.update(house)
    }
    fun deleteHaus(house: HouseJavier) = viewModelScope.launch {
        repository.delete(house)
    }
    fun deathforathousandyears() = viewModelScope.launch {
        repository.deleteAll()
    }
}