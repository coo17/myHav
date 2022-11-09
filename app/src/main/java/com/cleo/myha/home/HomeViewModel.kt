package com.cleo.myha.home

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date
import java.util.HashMap

class HomeViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _todayTasks = MutableLiveData<List<Habits>>()
    val todayTasks: LiveData<List<Habits>>
        get() = _todayTasks

    private val _completedTask = MutableLiveData<List<Habits>>()
    val completedTask: LiveData<List<Habits>>
    get() = _completedTask

//    private val weekday = 6
//    val daySelected= when(weekday){
//
//    }

    init {
        getTodayTasks()

    }

    private fun getTodayTasks() {

        db.collection("habits")
//            .whereEqualTo("ownerId", "Cleo@gmail.com")
            .get()
            .addOnSuccessListener { result ->
//                Log.d("Cleooo", "get success ,${result.documents}")
                val list = mutableListOf<Habits>()
                Log.d("Cleoo","${result.size()}")

                for (i in result) {
                    Log.d("Cleoo","id=${i.get("id").toString()}")

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
                        i.get("timer").toString(),
                        i.get("createdTime").toString().toLong(),
                        i.get("startedDate").toString().toLong(),
                        i.get("endDate").toString().toLong()
                        )
                    Log.d("OMG","${ i.get("endDate").toString().toLong()}")
                    list.add(todayTaskData)

                }

                Log.d("Cleoo","${list.size}")
                list.sortBy { it.reminder }
                _todayTasks.value = list
            }
            .addOnFailureListener { exception ->
                Log.d("Cleooo", "get fail")
                Log.w("Cleooo", "Error getting documents.", exception)
            }
    }


    fun sendCompletedTask(data: Habits){

//        val task = FirebaseFirestore.getInstance().collection("habits")

        val document = data.id
        val userEmail = "cleo@gmail.com"
        val nowTime = Date().time

        val completedDailyTask: MutableMap<String, Any> = HashMap()
        completedDailyTask["email"] = userEmail
        completedDailyTask["id"] = document
        completedDailyTask["completedTime"] = nowTime

                db.collection("habits")
                    .document(document)
                    .collection("completedTask")
                    .document()
                    .set(completedDailyTask)

            .addOnSuccessListener {
                Log.d("Cleooo","Success! ${nowTime}")
            }
            .addOnFailureListener {
                Log.d("Cleooo", "oh, it's fail")
            }
    }
}



