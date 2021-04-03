package com.example.vehiclefragment.db

import androidx.annotation.WorkerThread
import com.example.vehiclefragment.db.dao.VehicleDao
import com.example.vehiclefragment.db.entities.VehicleItem
import kotlinx.coroutines.flow.Flow

class VehicleRepositiry(private val vehicleDao: VehicleDao) {

    val allVehicle: Flow<List<VehicleItem>> = vehicleDao.getAllVehicle()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(vehicle: VehicleItem) {
        vehicleDao.insert(vehicle)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(vehicle: VehicleItem) {
        vehicleDao.update(vehicle)
    }
}