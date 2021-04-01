package com.example.vehiclefragment.interfaces

import com.example.vehiclefragment.data.VehicleItem

interface IVehicleToEditListener {
    fun toEdit(item: VehicleItem)
    fun toList()
}