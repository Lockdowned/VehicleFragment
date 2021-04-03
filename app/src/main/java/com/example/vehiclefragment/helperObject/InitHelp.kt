package com.example.vehiclefragment.helperObject

import android.content.Context
import com.example.vehiclefragment.db.entities.VehicleItem

object InitHelp {
    fun initialize(context: Context): MutableList<VehicleItem>{
    return mutableListOf(
        VehicleItem(
            "Mini Cooper 2009",
            "1000 petrol, BS 2, 100 hp",
            "Run 500 km, blink led"
        ),
        VehicleItem(
            "Honda Accord 2011",
            "1800 diesel, DG 4, 130 hp",
            "Run 1200 km, need change wheels"
        )
    )
}
}