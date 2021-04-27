package com.example.vehiclefragment.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.work.*
import com.example.vehiclefragment.repos.VehicleRepositiry
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.db.workers.SubscribeToFirebase
import com.example.vehiclefragment.db.workers.SyncDatabaseWorker
import com.example.vehiclefragment.interfaces.CommonActionDatabases
import com.example.vehiclefragment.repos.FirestoreRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dalvik.annotation.TestTarget
import kotlinx.coroutines.*
import org.jetbrains.annotations.TestOnly
import java.lang.IllegalArgumentException

class VehicleViewModel(
        application: Application,
        private val repositoryRoom: VehicleRepositiry,
        private val repositoryFire: FirestoreRepository ): AndroidViewModel(application) { // earlier ViewModel()

    init {
        syncDatabases(WorkManager.getInstance(application))
    }

    val allVehicle: LiveData<List<VehicleItem>> = repositoryRoom.allVehicle.asLiveData()

    var selectedIdVehicle: Int? = null

    fun syncDatabases(workManager: WorkManager){
        val firstSyncDatabases = OneTimeWorkRequestBuilder<SyncDatabaseWorker>()
                .addTag("work")
                .build()

        val subscribeToFirebase = OneTimeWorkRequestBuilder<SubscribeToFirebase>()
                .addTag("work")
                .build()

        workManager
                .beginWith(firstSyncDatabases)
                .then(subscribeToFirebase)
                .enqueue()

//        workManager.getWorkInfoByIdLiveData(firstSyncDatabases.id) // example observing progress workManager
//            .observeForever(Observer { workInfo ->
//            if (workInfo != null) {
//                val progress = workInfo.progress
//                val value = progress.getInt("Progress", 0)
//                Log.d("HEY", "Still alive, message: $value")
//            }
//        })
    }


    fun insertRoom(vehicle: VehicleItem) = viewModelScope.launch(Dispatchers.IO) {
        repositoryRoom.insert(vehicle)
        repositoryFire.insert(repositoryRoom.getAllForSync().last()) // так работает корректно
    }


    fun update(vehicle: VehicleItem) = viewModelScope.launch {
        repositoryRoom.update(vehicle)
        repositoryFire.update(vehicle)
    }
}

class VehicleViewModelFactory(
        private val application: Application,
        private val repositoryRoom: VehicleRepositiry,
        private val repositoryFire: FirestoreRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return VehicleViewModel(application, repositoryRoom, repositoryFire) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}