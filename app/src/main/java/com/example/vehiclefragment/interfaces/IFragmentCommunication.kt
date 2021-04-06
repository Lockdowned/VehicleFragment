package com.example.vehiclefragment.interfaces

import com.example.vehiclefragment.db.entities.VehicleItem

interface IFragmentCommunication {
    fun toEdit(vehicleId: Int)
    fun toList()
}