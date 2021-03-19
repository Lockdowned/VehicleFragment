package com.example.vehiclefragment.data

import android.graphics.drawable.Drawable

data class VehicleItem(
    val img: Drawable?,
    val brandAndModel: String,
    val specification: String,
    var serviceInfo: String
)
