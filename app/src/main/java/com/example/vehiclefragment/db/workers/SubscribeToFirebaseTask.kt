package com.example.vehiclefragment.db.workers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.vehiclefragment.VehicleApplication
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscribeToFirebaseTask(
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params) {

    private val roomRepos = (applicationContext as VehicleApplication).taskRepository
    private val firestoreReposTask = (applicationContext as VehicleApplication).taskFirestoreRepos

    override fun doWork(): Result {
        val vehicleCollectionFirestore = Firebase.firestore.collection("tasks")
        CoroutineScope(Dispatchers.IO).launch {
            val taskRoomList = roomRepos.getAllForSync()
            val firestoreList = firestoreReposTask.getAllForSync()

            if (firestoreList.isEmpty()) {
                for (task in taskRoomList) {
                    firestoreReposTask.insert(task)
                }
            }
        }

        vehicleCollectionFirestore.addSnapshotListener { value, error ->
            error?.let {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            CoroutineScope(Dispatchers.IO).launch {
                value?.let {
                    Log.d("HEY", "trigger snapshot")
                    val roomList = roomRepos.getAllForSync()
                    val actualList = firestoreReposTask.getAllForSync()
                    for (doc in actualList){
                        val matcVehicle = roomList.find { it.id == doc.id}
                        if (matcVehicle == null){
                            roomRepos.insert(doc)
                            Log.d("HEY", "insert : $doc")
                        } else if (matcVehicle != doc) { // in kotlin equals, == for data classes are same
                            Log.d("HEY", "update")
                            roomRepos.update(doc)
                        }
                    }
                }
            }
        }
        return Result.success()
    }
}