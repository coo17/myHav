package com.cleo.myha.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class HomeTaskViewModel : ViewModel() {



    private val _todayTasks = MutableLiveData<List<Habits>>()
    val todayTasks: LiveData<List<Habits>>
        get() = _todayTasks

    init {
        getTodayTasks()
    }

    private fun getTodayTasks() {
        val db = FirebaseFirestore.getInstance()

        db.collection("habits")
//            .whereEqualTo("ownerId", "Cleo@gmail.com")
            .get()
            .addOnSuccessListener { result ->
//                Log.d("Cleooo", "get success ,${result.documents}")
                val list = mutableListOf<Habits>()
                Log.d("Vicc","${result.size()}")

                for (i in result) {
                    Log.d("Vicc","id=${i.get("id").toString()}")

//                    val todayTaskData = i.toObject(Habits::class.java)
                    val members = i.get("members") as List<String>
                    val repeatedDays = i.get("repeatedDays") as List<Boolean>

                    val todayTaskData = Habits(
                        i.get("id").toString(),
                        i.get("ownerID").toString(),
                        members,
                        i.get("category").toString(),
                        i.get("task").toString(),
                        repeatedDays,
                        i.get("duration").toString(),
                        i.get("reminder").toString().toLong(),
                        i.get("timer").toString()
                    )
//                    Log.d("Cleooo", "$repeatedDays")
//                    Log.d("Cleooo", "${document.id} => ${document.data}")
//                    Log.d("Cleooo", "$newHa")
                    list.add(todayTaskData)
                }
                Log.d("Vicc","${list.size}")
                list.sortBy { it.reminder }
                _todayTasks.value = list
            }
            .addOnFailureListener { exception ->
                Log.d("Cleooo", "get fail")
                Log.w("Cleooo", "Error getting documents.", exception)
            }
    }
}


