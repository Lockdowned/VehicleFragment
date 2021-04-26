package com.example.vehiclefragment.viewmodels

import androidx.lifecycle.*
import com.example.vehiclefragment.repos.VehicleRepositiry
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.repos.FirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class VehicleViewModel(
        private val repositoryRoom: VehicleRepositiry,
        private val repositoryFire: FirestoreRepository ): ViewModel() {

    val allVehicle: LiveData<List<VehicleItem>> = repositoryRoom.allVehicle.asLiveData()

    var selectedIdVehicle: Int? = null

    fun insertRoom(vehicle: VehicleItem) = viewModelScope.launch {
        repositoryRoom.insert(vehicle)
        delay(500)
        repositoryFire.insert(allVehicle.value!!.last()) // TEMPORARY
    }

    fun insertFirestore(vehicle: VehicleItem) = viewModelScope.launch {
        repositoryFire.insert(vehicle)
    }


    fun update(vehicle: VehicleItem) = viewModelScope.launch {
        repositoryRoom.update(vehicle)
    }
}

class VehicleViewModelFactory(
        private val repositoryRoom: VehicleRepositiry,
        private val repositoryFire: FirestoreRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return VehicleViewModel(repositoryRoom, repositoryFire) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}