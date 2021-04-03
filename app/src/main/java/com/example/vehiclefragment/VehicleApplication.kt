package com.example.vehiclefragment

import android.app.Application
import com.example.vehiclefragment.db.VehicleRepositiry
import com.example.vehiclefragment.db.VehicleRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class VehicleApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val databse by lazy { VehicleRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { VehicleRepositiry(databse.vehicleDao()) }
}