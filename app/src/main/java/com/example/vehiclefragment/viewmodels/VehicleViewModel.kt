package com.example.vehiclefragment.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vehiclefragment.data.VehicleItem
import com.example.vehiclefragment.helperObject.InitHelp

class VehicleViewModel: ViewModel() {

    val allVehicle = MutableLiveData<MutableList<VehicleItem>>(mutableListOf())
    private var selectedVehicle: VehicleItem? = null

    fun select(item: VehicleItem){
        selectedVehicle = item
    }

    fun getSelected(): VehicleItem? {
        return selectedVehicle
    }
}