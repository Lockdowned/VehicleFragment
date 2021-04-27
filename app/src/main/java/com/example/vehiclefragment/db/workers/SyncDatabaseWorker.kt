package com.example.vehiclefragment.db.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.vehiclefragment.VehicleApplication
import kotlinx.coroutines.*

class SyncDatabaseWorker(
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params) {

    private val roomRepos = (applicationContext as VehicleApplication).vehicleRoomRepository
    private val firestoreRepos = (applicationContext as VehicleApplication).vehicleFirestoreRepository

    override fun doWork(): Result {
        val roomList = roomRepos.getAllSync()
        val firestoreList = firestoreRepos.getAll()
        var job: Job? = null

        if (firestoreList.isEmpty()){
            for (vehicle in roomList){
                firestoreRepos.insert(vehicle)
            }
        } else {
            job = CoroutineScope(Dispatchers.IO).launch {
                for (remoteVehicle in firestoreList){
                    val matchVehicle = roomList.find { it.id == remoteVehicle.id}
                    if (matchVehicle == null) {
                        roomRepos.insert(remoteVehicle)
                    } else {
                        roomRepos.update(remoteVehicle)
                    }
                }
            }
        }
        runBlocking {
            job?.join()
        }
        return Result.success()
    }

}