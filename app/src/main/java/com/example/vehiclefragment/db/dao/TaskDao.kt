package com.example.vehiclefragment.db.dao

import androidx.room.*
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.db.entities.relations.VehicleWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskItem)

    @Transaction
    @Query("SELECT * FROM vehicle_table WHERE id = :idInVehicle")
    suspend fun getVehicleWithTasks(idInVehicle: Int): List<VehicleWithTasks>

    @Update
    suspend fun updateTask(task: TaskItem)

//    @Query("SELECT * FROM task_table WHERE id_vehicle = :idInVehicle")
//    suspend fun getTasks(idInVehicle: Int): List<TaskItem>

    @Query("SELECT * FROM task_table WHERE id_vehicle = :idInVehicle")
    fun getTasks(idInVehicle: Int): Flow<List<TaskItem>>

}