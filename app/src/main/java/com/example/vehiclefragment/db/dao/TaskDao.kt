package com.example.vehiclefragment.db.dao

import androidx.room.*
import com.example.vehiclefragment.db.entities.TaskItem
import com.example.vehiclefragment.db.entities.relations.VehicleWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskItem)

    @Query("SELECT * FROM vehicle_table WHERE id = :idInVehicle")
    fun getVehicleWithTasks(idInVehicle: Int): Flow<List<VehicleWithTasks>>

//    @Query("SELECT * FROM vehicle_table WHERE id = :idInVehicle")
//    fun getVehicleWithTasks(idInVehicle: Int): List<VehicleWithTasks> // почему нельзя брать просто List

    @Update
    suspend fun updateTask(task: TaskItem)

    @Transaction
    @Query("DELETE FROM task_table WHERE id = :taskId")
    suspend fun delete(taskId: Int)
}