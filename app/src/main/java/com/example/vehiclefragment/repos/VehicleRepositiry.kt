package com.example.vehiclefragment.repos

import com.example.vehiclefragment.db.dao.ImagesDao
import com.example.vehiclefragment.db.dao.VehicleDao
import com.example.vehiclefragment.db.entities.ImagesItem
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.interfaces.CommonActionDatabases
import kotlinx.coroutines.flow.Flow

class VehicleRepositiry(
    private val vehicleDao: VehicleDao,
    private val imgDao: ImagesDao
): CommonActionDatabases {

    val allVehicle: Flow<List<VehicleItem>> = vehicleDao.getAllVehicle()

    val allImages: Flow<List<ImagesItem>> = imgDao.getAllImgLive()

    override suspend fun getAllForSync(): MutableList<VehicleItem> {
        return vehicleDao.getAllSync()
    }

    override suspend fun insert(vehicleItem: VehicleItem) {
        vehicleDao.insert(vehicleItem)
    }


    override suspend fun update(vehicleItem: VehicleItem) {
        vehicleDao.update(vehicleItem)
    }

    suspend fun getAllImg(): MutableList<ImagesItem> {
        return imgDao.getAllImg()
    }

    suspend fun insertImg(imagesItem: ImagesItem) {
        imgDao.insert(imagesItem)
    }

    override suspend fun delete(vehicleItem: VehicleItem) {
        TODO("Not yet implemented")
    }
}