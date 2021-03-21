package com.example.vehiclefragment.data

import android.graphics.drawable.Drawable

data class VehicleItem(
        var img: Drawable?,
        var brandAndModel: String,
        var specification: String,
        var serviceInfo: String,
        var uriString: String?
)
