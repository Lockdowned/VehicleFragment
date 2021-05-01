package com.example.vehiclefragment.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.db.entities.relations.VehicleWithTasks
import com.example.vehiclefragment.db.workers.SubscribeToFirebaseTask
import com.example.vehiclefragment.repos.FirebaseReposTask
import com.example.vehiclefragment.repos.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(
    application: Application,
    private val repository: TaskRepository,
    private val repositoryFire: FirebaseReposTask
): AndroidViewModel(application) {

    init {
        workerDatabases(WorkManager.getInstance(application))
    }

    private fun workerDatabases(workManager: WorkManager){

        val subscribeToFirebase = OneTimeWorkRequestBuilder<SubscribeToFirebaseTask>()
            .addTag("snapshotFirebaseTask")
            .build()

        workManager
            .beginWith(subscribeToFirebase)
            .enqueue()
    }

    var vehicleWithTasks: LiveData<List<VehicleWithTasks>>? = null

    fun setVehicleWithTasks(id: Int){
        getVehicleWithTasks(id)
    }

    private fun getVehicleWithTasks(id: Int)  = viewModelScope.launch {
         vehicleWithTasks = repository.getVehicleWithTasks(id).asLiveData()
    }

    fun insertTask(taskItem: TaskItem) = viewModelScope.launch {
        repository.insert(taskItem)
        repositoryFire.insert(repository.getAllForSync().last())
    }

    fun update(taskItem: TaskItem) = viewModelScope.launch {
        repository.update(taskItem)
        repositoryFire.update(taskItem)
    }

    fun delete(taskItem: TaskItem) = viewModelScope.launch {
        repository.delete(taskItem)
    }

}

class TaskViewModelFactory(
    private val application: Application,
    private val repository: TaskRepository,
    private val repositoryFire: FirebaseReposTask
    ): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TaskViewModel(application, repository, repositoryFire) as T
    }

}