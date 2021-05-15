package com.example.vehiclefragment.repos

import android.util.Log
import com.example.vehiclefragment.db.entities.TaskItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirebaseReposTask {

    private val logTag = javaClass.name

    private val taskCollection = Firebase.firestore.collection("tasks")

    suspend fun insert(task: TaskItem){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                taskCollection.add(task).await()
                Log.d(logTag, "Successful insert task")
            } catch (e:Exception){
                Log.d(logTag, e.message!!)
            }
        }
    }

    suspend fun getAllForSync(): List<TaskItem> {
        val list = mutableListOf<TaskItem>()
        taskCollection.get().addOnSuccessListener { notDeserializedList ->
            for (document in notDeserializedList) {
                val taskItem = document.toObject<TaskItem>()
                list.add(taskItem)
            }
        }.await()
        return list
    }

    suspend fun update(taskItem: TaskItem) {
        val necessaryDoc = taskCollection.whereEqualTo("id", taskItem.id).get().await()
        if (!necessaryDoc.isEmpty) {
            taskCollection.document(necessaryDoc.first().id).set(taskItem)
        }
    }
}