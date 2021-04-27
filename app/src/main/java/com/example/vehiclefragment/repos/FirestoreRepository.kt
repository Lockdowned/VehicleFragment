package com.example.vehiclefragment.repos

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.vehiclefragment.db.entities.VehicleItem
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val logTag = javaClass.name

    private val vehicleCollection = Firebase.firestore.collection("vehicles")

    fun insert(vehicle: VehicleItem){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                vehicleCollection.add(vehicle).await()
                Log.d(logTag, "Successful insert vehicle")
            } catch (e:Exception){
                Log.d(logTag, e.message!!)
            }
        }
    }

    fun getAll(): List<VehicleItem> {
        val list = mutableListOf<VehicleItem>()
        val task = vehicleCollection.get().addOnSuccessListener { notDeserializedList ->
            for (document in notDeserializedList) {
                val vehicleItem = document.toObject<VehicleItem>()
                list.add(vehicleItem)
            }
        }
        runBlocking {
            task.await()
        }
        return list
    }
}