package com.example.vehiclefragment.viewmodels

import androidx.lifecycle.*
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.db.entities.relations.VehicleWithTasks
import com.example.vehiclefragment.repos.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository): ViewModel() {

    var vehicleWithTasks: LiveData<List<VehicleWithTasks>>? = null

    fun setVehicleWithTasks(id: Int){
        getVehicleWithTasks(id)
    }

    private fun getVehicleWithTasks(id: Int)  = viewModelScope.launch {
         vehicleWithTasks = repository.getVehicleWithTasks(id).asLiveData()
    }

    fun insertTask(taskItem: TaskItem) = viewModelScope.launch {
        repository.insert(taskItem)
    }

    fun update(taskItem: TaskItem) = viewModelScope.launch {
        repository.update(taskItem)
    }

    fun delete(taskId: Int) = viewModelScope.launch {
        repository.delete(taskId)
    }

}

class TaskViewModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TaskViewModel(repository) as T
    }

}