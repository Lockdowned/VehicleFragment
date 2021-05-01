package com.example.vehiclefragment.viewmodels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.example.vehiclefragment.db.entities.ImagesItem
import com.example.vehiclefragment.repos.VehicleRepositiry
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.db.workers.SubscribeToFirebase
import com.example.vehiclefragment.db.workers.SyncDatabaseWorker
import com.example.vehiclefragment.repos.FirebaseRepository
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class VehicleViewModel(
        application: Application,
        private val repositoryRoom: VehicleRepositiry,
        private val repositoryFire: FirebaseRepository ): AndroidViewModel(application) { // earlier ViewModel()

    init {
        Log.d("HEY", "start")
        workerDatabases(WorkManager.getInstance(application))
    }

    var lastImg: String? = null

    val allVehicle: LiveData<List<VehicleItem>> = repositoryRoom.allVehicle.asLiveData()
    val allImages: LiveData<List<ImagesItem>> = repositoryRoom.allImages.asLiveData()
    var currentListImg: List<ImagesItem>? = null

    var selectedIdVehicle: Int? = null

    private fun workerDatabases(workManager: WorkManager){
        val firstSyncDatabases = OneTimeWorkRequestBuilder<SyncDatabaseWorker>()
                .addTag("firstSync")
                .build()

        val subscribeToFirebase = OneTimeWorkRequestBuilder<SubscribeToFirebase>()
                .addTag("snapshotFirebase")
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

    fun insertImgToCloud(fileName: Int, currentFile: Uri) = CoroutineScope(Dispatchers.IO).launch {
        repositoryFire.insertImgToCloud(fileName, currentFile)
    }

    fun getImg(vehicleId: Int): String {
        return currentListImg?.find { it.id == vehicleId }?.imgVehicle ?: ""
    }

    fun insertImg(imagesItem: ImagesItem) = viewModelScope.launch(Dispatchers.IO){
        repositoryRoom.insertImg(imagesItem)
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
        private val repositoryFire: FirebaseRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return VehicleViewModel(application, repositoryRoom, repositoryFire) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}