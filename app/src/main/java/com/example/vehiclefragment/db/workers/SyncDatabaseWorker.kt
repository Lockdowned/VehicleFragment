package com.example.vehiclefragment.db.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.vehiclefragment.VehicleApplication
import com.example.vehiclefragment.db.entities.ImagesItem
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class SyncDatabaseWorker(
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params) {

    private val vehicleRoomRepos = (applicationContext as VehicleApplication).vehicleRoomRepository
    private val firestoreRepos = (applicationContext as VehicleApplication).vehicleFirestoreRepository

    override fun doWork(): Result {
        val job = CoroutineScope(Dispatchers.IO).launch {
            val vehicleRoomList = vehicleRoomRepos.getAllForSync()
            val firestoreList = firestoreRepos.getAllForSync()
            val imgRoomList = vehicleRoomRepos.getAllImg()

            if (firestoreList.isEmpty()){
                for (vehicle in vehicleRoomList){
                    firestoreRepos.insert(vehicle)
                }
            } else {
                for (remoteVehicle in firestoreList){
                    val matchVehicle = vehicleRoomList.find { it.id == remoteVehicle.id}
                    if (matchVehicle == null) {
                        vehicleRoomRepos.insert(remoteVehicle)
                    } else if(matchVehicle != remoteVehicle){
                        vehicleRoomRepos.update(remoteVehicle)
                    }
                }
            }

            val cloudImg = firestoreRepos.imageRef.child("images/").listAll().await()
            val listStringImg = mutableMapOf<Int ,String>()
            for (image in cloudImg.items){
                listStringImg.put(image.name.toInt() , image.downloadUrl.await().toString())
            }
            for (vehicle in vehicleRoomList){
                val refId = vehicle.img
                if (refId != -1 && imgRoomList.find { it.id == refId} == null){
                    val image = ImagesItem(
                            listStringImg.get(vehicle.img)!!,
                            refId
                    )
                   vehicleRoomRepos.insertImg(image)
                }
            }
        }
        runBlocking { // need try without blocking
            job.join()
        }
        return Result.success()
    }

}