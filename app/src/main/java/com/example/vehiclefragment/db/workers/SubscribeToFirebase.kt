package com.example.vehiclefragment.db.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.vehiclefragment.VehicleApplication
import com.example.vehiclefragment.db.entities.VehicleItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class SubscribeToFirebase(
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
            value?.let {
                Log.d("HEY", "i'm HERE")
                CoroutineScope(Dispatchers.IO).launch{
                    val roomList = roomRepos.getAllSync()
                    val actualList = firestoreRepos.getAll()
                    for (doc in actualList){
                        val matcVehicle = roomList.find { it.id == doc.id}
                        if (matcVehicle == null){
                            roomRepos.insert(doc)
                        }
                    }
                }
            }
        }
        return Result.success()
    }
}