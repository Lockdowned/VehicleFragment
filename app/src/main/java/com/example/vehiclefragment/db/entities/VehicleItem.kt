package com.example.vehiclefragment.db.entities

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_table")
data class VehicleItem(
        @ColumnInfo(name = "brand_and_model") var brandAndModel: String,
        var specification: String,
        @ColumnInfo(name = "service_info")var serviceInfo: String,
        @ColumnInfo(name = "uri_string") var uriString: String? = null,
        @ColumnInfo var img: String? = null,
        @PrimaryKey(autoGenerate = true) val id: Int? = null
)
