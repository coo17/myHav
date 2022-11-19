package com.cleo.myha.discover.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.firestore.FirebaseFirestore

class CommunityViewModel : ViewModel() {

    private val firebase = FirebaseFirestore.getInstance()

    private val _groupTask = MutableLiveData<List<Habits>>()
    val groupTask: LiveData<List<Habits>>
        get() = _groupTask

    init {
        getGroupTask()
    }

    private fun getGroupTask(){

        firebase.collection("habits")
            .get()
            .addOnSuccessListener { result ->

                val list = mutableListOf<Habits>()

                for (i in result) {
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
                        i.get("endDate").toString().toLong(),
                        i.get("mode").toString().toInt()
                    )

                    list.add(todayTaskData)
                }

                list.sortBy { it.reminder }
                _groupTask.value = list
            }
            .addOnFailureListener { exception ->
                Log.d("Cleooo", "get fail")
                Log.w("Cleooo", "Error getting documents.", exception)
            }

    }
}