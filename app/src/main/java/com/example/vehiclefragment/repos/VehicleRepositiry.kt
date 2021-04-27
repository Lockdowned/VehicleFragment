package com.example.vehiclefragment.repos

import androidx.annotation.WorkerThread
import com.example.vehiclefragment.db.dao.VehicleDao
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.interfaces.CommonActionDatabases
import kotlinx.coroutines.flow.Flow

class VehicleRepositiry(private val vehicleDao: VehicleDao): CommonActionDatabases {

    val allVehicle: Flow<List<VehicleItem>> = vehicleDao.getAllVehicle()

    override suspend fun getAllForSync() : MutableList<VehicleItem> {
        return vehicleDao.getAllSync()
    }

    override suspend fun insert(vehicleItem: VehicleItem) {
        vehicleDao.insert(vehicleItem)
    }


    override suspend fun update(vehicleItem: VehicleItem) {
        vehicleDao.update(vehicleItem)
    }

    override suspend fun delete(vehicleItem: VehicleItem) {
        TODO("Not yet implemented")
    }
}