package com.cleo.myha.habits

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleo.myha.data.Habits
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HabitViewModel: ViewModel() {

    private val firebase = FirebaseFirestore.getInstance()

    private val _singleTasks = MutableLiveData<List<Habits>>()
    val singleTask: LiveData<List<Habits>>
        get() = _singleTasks

    init {
        getSingleTasks()
    }

    private fun getSingleTasks() = viewModelScope.launch {

        firebase.collection("habits")
            .get()
            .addOnSuccessListener { result ->
//                Log.d("Cleooo", "get success ,${result.documents}")

                val list = mutableListOf<Habits>()
                Log.d("Vicc","${result.size()}")

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
                        i.get("timer").toString()
                    )

                    list.add(todayTaskData)
                }
                Log.d("Vicc","${list.size}")
                list.sortBy { it.reminder }
                _singleTasks.value = list
            }
            .addOnFailureListener { exception ->
                Log.d("Cleooo", "get fail")
                Log.w("Cleooo", "Error getting documents.", exception)
            }
    }

}