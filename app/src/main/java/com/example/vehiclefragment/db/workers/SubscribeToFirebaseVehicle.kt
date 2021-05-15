package com.example.vehiclefragment.db.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.vehiclefragment.VehicleApplication
import com.example.vehiclefragment.db.entities.ImagesItem
import com.example.vehiclefragment.db.entities.VehicleItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class SubscribeToFirebaseVehicle(
        ctx: Context,
        params: WorkerParameters
) : Worker(ctx, params) {

    private val roomRepos = (applicationContext as VehicleApplication).vehicleRoomRepository
    private val firestoreRepos = (applicationContext as VehicleApplication).vehicleFirestoreRepository

    override fun doWork(): Result {
        val vehicleCollectionFirestore = Firebase.firestore.collection("vehicles")
        vehicleCollectionFirestore.addSnapshotListener { value, error ->
            error?.let {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                value?.let {
                    Log.d("HEY", "trigger snapshot")
                    val vehicleRoomList = roomRepos.getAllForSync()
                    val actualList = firestoreRepos.getAllForSync()

                    for (vehicleRemote in actualList){
                        val matcVehicle = vehicleRoomList.find { it.id == vehicleRemote.id}
                        if (matcVehicle == null){
                            roomRepos.insert(vehicleRemote)
                            Log.d("HEY", "insert")
                            insertImg(vehicleRemote)
                        } else if (matcVehicle != vehicleRemote) { // in kotlin equals, == for data classes are same
                            roomRepos.update(vehicleRemote)
                            Log.d("HEY", "update")
                        }
                    }
                }
            }
        }
        return Result.success()
    }

    private suspend fun insertImg(vehicleItem: VehicleItem){
        if (vehicleItem.img != -1) {
            val imgId = vehicleItem.img
            val imgRoomList = roomRepos.getAllImg()
            val cloudImg = firestoreRepos.imageRef
                    .child("images/").listAll().await()
            val necessaryImg = cloudImg.items.find { it.name == imgId.toString() }?.downloadUrl?.await().toString()
            Log.d("HEY", "necessaryImg: $necessaryImg" )
            if (necessaryImg != "null" && imgRoomList.find { it.id == imgId } == null){
                val image = ImagesItem(
                        necessaryImg,
                        imgId
                )
                roomRepos.insertImg(image)
                Log.d("HEY", "Insert image in snapshot to room $image")
            }
        }
    }
}