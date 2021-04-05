package com.example.vehiclefragment.viewmodels

import androidx.lifecycle.*
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.db.entities.relations.VehicleWithTasks
import com.example.vehiclefragment.repos.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository): ViewModel() {

    private var vehicleWithTasks: List<VehicleWithTasks>? = null

    fun getList(id: Int): List<VehicleWithTasks>?{
        getVehicleWithTasks(id)
        return vehicleWithTasks
    }

    private fun getVehicleWithTasks(id: Int)  = viewModelScope.launch {
         vehicleWithTasks = repository.getVehicleWithTasks(id)
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

//    private var tasks: List<TaskItem>? = null
//
//    fun tasks(id: Int): List<VehicleWithTasks>?{
//        getTasks(id)
//        return vehicleWithTasks
//    }
//
//    private fun getTasks(id: Int)  = viewModelScope.launch {
//        tasks = repository.getTasks(id)
//    }

    var allTasks: LiveData<List<TaskItem>>? = null

    fun setTasks(idVehicle: Int) = viewModelScope.launch {
        allTasks = repository.getTasks(idVehicle).asLiveData()
    }


}

class TaskViewModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TaskViewModel(repository) as T
    }

}