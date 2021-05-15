package com.example.vehiclefragment.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images_table")
data class ImagesItem(
    @ColumnInfo (name = "img_vehicle") var imgVehicle: String = "",

    @PrimaryKey val id: Int
)