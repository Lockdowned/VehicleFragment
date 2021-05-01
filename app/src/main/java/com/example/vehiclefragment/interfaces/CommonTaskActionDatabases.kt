package com.example.vehiclefragment.interfaces

import com.example.vehiclefragment.db.entities.TaskItem

interface CommonTaskActionDatabases {
    suspend fun getAllForSync(): List<TaskItem>

    suspend fun insert(taskItem: TaskItem)

    suspend fun update(taskItem: TaskItem)

    suspend fun delete(taskItem: TaskItem)
}