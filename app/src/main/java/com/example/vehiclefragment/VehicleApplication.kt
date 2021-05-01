package com.example.vehiclefragment

import android.app.Application
import com.example.vehiclefragment.repos.VehicleRepositiry
import com.example.vehiclefragment.db.VehicleRoomDatabase
import com.example.vehiclefragment.repos.FirebaseReposTask
import com.example.vehiclefragment.repos.FirebaseRepository
import com.example.vehiclefragment.repos.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class VehicleApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val databse by lazy { VehicleRoomDatabase.getDatabase(this, applicationScope) }
    val vehicleRoomRepository by lazy { VehicleRepositiry(databse.vehicleDao(), databse.imgDao()) }
    val vehicleFirestoreRepository by lazy { FirebaseRepository() }
    val taskFirestoreRepos by lazy { FirebaseReposTask() }
    val taskRepository by lazy { TaskRepository(databse.taskDao()) }
}