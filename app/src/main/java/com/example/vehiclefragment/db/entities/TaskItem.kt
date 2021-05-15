package com.example.vehiclefragment.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskItem(
        var checkBox: Boolean = false,
        var taskText: String = "",
        @ColumnInfo(name = "id_vehicle") var idVehicle: Int = -1,
        @PrimaryKey(autoGenerate = true) val id: Int? = null
)
