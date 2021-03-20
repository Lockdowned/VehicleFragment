package com.example.vehiclefragment.interfaces

import com.example.vehiclefragment.data.VehicleItem

interface IVehicleCreateListener {
    fun deliverCreatedVehicle(vehicleItem: VehicleItem)
}