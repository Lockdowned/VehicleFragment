package com.example.vehiclefragment.helperObject

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import com.example.vehiclefragment.R
import com.example.vehiclefragment.data.VehicleItem

object InitHelp {
    fun initialize(context: Context): List<VehicleItem>{
        return mutableListOf(
            VehicleItem(
                AppCompatResources.getDrawable(context, R.drawable.mini_cooper_angularfront),
                "Mini Cooper 2009",
                "1000 petrol, BS 2, 100 hp",
                "Run 500 km, blink led"
            ),
            VehicleItem(
                AppCompatResources.getDrawable(context, R.drawable.honda_accord),
                "Honda Accord 2011",
                "1800 diesel, DG 4, 130 hp",
                "Run 1200 km, need change wheels"
            )
        )
    }
}