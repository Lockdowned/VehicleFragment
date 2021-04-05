package com.example.vehiclefragment.repos

import androidx.annotation.WorkerThread
import com.example.vehiclefragment.db.dao.TaskDao
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.db.entities.relations.VehicleWithTasks
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getVehicleWithTasks(id: Int): List<VehicleWithTasks>{
        return taskDao.getVehicleWithTasks(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(taskItem: TaskItem){
        taskDao.insert(taskItem)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(taskItem: TaskItem){
        taskDao.updateTask(taskItem)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(taskId: Int){
        taskDao.delete(taskId)
    }

//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun getTasks(idVehicle: Int): List<TaskItem>{
//        return taskDao.getTasks(idVehicle)
//    }

    fun getTasks(idVehicle: Int): Flow<List<TaskItem>>{
        return taskDao.getTasks(idVehicle)
    }


}