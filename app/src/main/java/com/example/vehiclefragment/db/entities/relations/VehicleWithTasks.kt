package com.example.vehiclefragment.db.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.db.entities.VehicleItem

data class VehicleWithTasks(
        @Embedded val vehicle: VehicleItem,
        @Relation(
                parentColumn = "id",
                entityColumn = "id_vehicle"
        )
        val tasks: List<TaskItem>
)
