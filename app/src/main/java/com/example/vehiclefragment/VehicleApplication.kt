package com.example.vehiclefragment

import android.app.Application
import com.example.vehiclefragment.repos.VehicleRepositiry
import com.example.vehiclefragment.db.VehicleRoomDatabase
import com.example.vehiclefragment.repos.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class VehicleApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val databse by lazy { VehicleRoomDatabase.getDatabase(this, applicationScope) }
    val vehicleRepository by lazy { VehicleRepositiry(databse.vehicleDao()) }
    val taskRepository by lazy { TaskRepository(databse.taskDao()) }
}