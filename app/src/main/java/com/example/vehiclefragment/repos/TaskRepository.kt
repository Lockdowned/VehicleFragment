package com.example.vehiclefragment.repos

import androidx.annotation.WorkerThread
import com.example.vehiclefragment.db.dao.TaskDao
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.db.entities.relations.VehicleWithTasks
import com.example.vehiclefragment.interfaces.CommonTaskActionDatabases
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao): CommonTaskActionDatabases {

    suspend fun getVehicleWithTasks(id: Int): Flow<List<VehicleWithTasks>>{
        return taskDao.getVehicleWithTasks(id)
    }

    override suspend fun getAllForSync(): List<TaskItem> {
        return taskDao.getAllSync()
    }


    override suspend fun insert(taskItem: TaskItem){
        taskDao.insert(taskItem)
    }



    override suspend fun update(taskItem: TaskItem){
        taskDao.updateTask(taskItem)
    }

    override suspend fun delete(taskItem: TaskItem){
        taskDao.delete(taskItem)
    }
}