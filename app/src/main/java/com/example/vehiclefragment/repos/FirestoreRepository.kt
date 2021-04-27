package com.example.vehiclefragment.repos

import android.util.Log
import com.example.vehiclefragment.db.entities.VehicleItem
import com.example.vehiclefragment.interfaces.CommonActionDatabases
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirestoreRepository: CommonActionDatabases {
    private val logTag = javaClass.name

    private val vehicleCollection = Firebase.firestore.collection("vehicles")

    override suspend fun insert(vehicleItem: VehicleItem){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                vehicleCollection.add(vehicleItem).await()
                Log.d(logTag, "Successful insert vehicle")
            } catch (e:Exception){
                Log.d(logTag, e.message!!)
            }
        }
    }

    override suspend fun getAllForSync(): List<VehicleItem> {
        val list = mutableListOf<VehicleItem>()
        vehicleCollection.get().addOnSuccessListener { notDeserializedList ->
            for (document in notDeserializedList) {
                val vehicleItem = document.toObject<VehicleItem>()
                list.add(vehicleItem)
            }
        }.await()
        return list
    }

    override suspend fun update(vehicleItem: VehicleItem) {
        val necessaryDoc = vehicleCollection.whereEqualTo("id", vehicleItem.id).get().await().first()
        vehicleCollection.document(necessaryDoc.id).set(vehicleItem)
    }

    override suspend fun delete(vehicleItem: VehicleItem) {
        TODO("Not yet implemented")
    }
}