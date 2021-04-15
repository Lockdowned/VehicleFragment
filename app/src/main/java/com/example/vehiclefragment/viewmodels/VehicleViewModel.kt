package com.example.vehiclefragment.viewmodels

import androidx.lifecycle.*
import com.example.vehiclefragment.repos.VehicleRepositiry
import com.example.vehiclefragment.db.entities.VehicleItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class VehicleViewModel(private val repository: VehicleRepositiry): ViewModel() {

    val allVehicle: LiveData<List<VehicleItem>> = repository.allVehicle.asLiveData()

    var selectedIdVehicle: Int? = null

    fun insert(vehicle: VehicleItem) = viewModelScope.launch {
        repository.insert(vehicle)
    }


    fun update(vehicle: VehicleItem) = viewModelScope.launch {
        repository.update(vehicle)
    }
}

class VehicleViewModelFactory(private val repository: VehicleRepositiry): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return VehicleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}