package com.example.vehiclefragment.repos

import androidx.annotation.WorkerThread
import com.example.vehiclefragment.db.dao.VehicleDao
import com.example.vehiclefragment.db.entities.VehicleItem
import kotlinx.coroutines.flow.Flow

class VehicleRepositiry(private val vehicleDao: VehicleDao) {

    val allVehicle: Flow<List<VehicleItem>> = vehicleDao.getAllVehicle()

    fun getAllSync() : MutableList<VehicleItem> {
        return vehicleDao.getAllSync()
    }

    @WorkerThread
    suspend fun insert(vehicle: VehicleItem) {
        vehicleDao.insert(vehicle)
    }

    @WorkerThread
    suspend fun update(vehicle: VehicleItem) {
        vehicleDao.update(vehicle)
    }
}