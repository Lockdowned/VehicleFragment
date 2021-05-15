package com.example.vehiclefragment.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicle_table")
data class VehicleItem(
        @ColumnInfo(name = "brand_and_model") var brandAndModel: String = "",
        var specification: String = "",
        @ColumnInfo(name = "service_info")var serviceInfo: String = "",
        @ColumnInfo var img: Int = -1,
        @PrimaryKey(autoGenerate = true) val id: Int? = null
)
