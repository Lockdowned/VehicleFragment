package com.example.vehiclefragment.interfaces

import com.example.vehiclefragment.db.entities.VehicleItem

interface IFragmentCommunication {
    fun toEdit(item: VehicleItem)
    fun toList()
}