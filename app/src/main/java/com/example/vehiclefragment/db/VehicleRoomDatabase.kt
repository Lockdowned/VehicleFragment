package com.example.vehiclefragment.db

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.vehiclefragment.R
import com.example.vehiclefragment.db.dao.VehicleDao
import com.example.vehiclefragment.db.entities.VehicleItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(VehicleItem::class), version = 1, exportSchema = false)
abstract class VehicleRoomDatabase: RoomDatabase(){

    abstract fun vehicleDao(): VehicleDao

    companion object{
        @Volatile
        private var INSTANCE: VehicleRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope): VehicleRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VehicleRoomDatabase::class.java,
                    "vehicle_database"
                )       .addCallback(VehicleDatabaseCallBack(scope))
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class VehicleDatabaseCallBack(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.vehicleDao())
                }
            }
        }

        suspend fun populateDatabase(vehicleDao: VehicleDao){
            vehicleDao.deleteAll()

            var vehicle = VehicleItem(
                "Mini Cooper 2009",
                "1000 petrol, BS 2, 100 hp",
                "Run 500 km, blink led",
            )
            vehicleDao.insert(vehicle)
            vehicle = VehicleItem(
                "Honda Accord 2011",
                "1800 diesel, DG 4, 130 hp",
                "Run 1200 km, need change wheels",
            )
            vehicleDao.insert(vehicle)
        }
    }


}