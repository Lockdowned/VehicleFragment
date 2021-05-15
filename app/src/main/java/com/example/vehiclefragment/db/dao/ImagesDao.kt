package com.example.vehiclefragment.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vehiclefragment.db.entities.ImagesItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagesDao {
    @Query("SELECT * FROM images_table")
    fun getAllImg(): MutableList<ImagesItem>

    @Query("SELECT * FROM images_table")
    fun getAllImgLive(): Flow<List<ImagesItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imagesItem: ImagesItem)
}