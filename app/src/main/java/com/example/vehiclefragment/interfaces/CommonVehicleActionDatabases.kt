package com.example.vehiclefragment.interfaces

import com.example.vehiclefragment.db.entities.VehicleItem

interface CommonVehicleActionDatabases {

    suspend fun getAllForSync(): List<VehicleItem>

    suspend fun insert(vehicleItem: VehicleItem)

    suspend fun update(vehicleItem: VehicleItem)

    suspend fun delete(vehicleItem: VehicleItem)
}