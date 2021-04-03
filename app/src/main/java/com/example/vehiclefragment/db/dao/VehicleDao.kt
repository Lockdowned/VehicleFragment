package com.example.vehiclefragment.db.dao

import androidx.room.*
import com.example.vehiclefragment.db.entities.VehicleItem
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle_table")
    fun getAllVehicle(): Flow<List<VehicleItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleItem)

    @Query("DELETE FROM vehicle_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(vararg vehicle: VehicleItem)
}