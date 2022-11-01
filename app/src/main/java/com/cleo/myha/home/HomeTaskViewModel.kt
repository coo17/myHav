package com.cleo.myha.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleo.myha.data.Habits
import com.cleo.myha.data.PomodoroData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class HomeTaskViewModel : ViewModel() {


    private val firebase = FirebaseFirestore.getInstance()

    private val _todayTasks = MutableLiveData<List<Habits>>()
    val todayTasks: LiveData<List<Habits>>
        get() = _todayTasks

    init {
        getTodayTasks()
    }

    private fun getTodayTasks() = viewModelScope.launch {

        firebase.collection("habits")
            .get()
            .addOnSuccessListener { result ->
//                Log.d("Cleooo", "get success ,${result.documents}")
                val list = mutableListOf<Habits>()

                for (i in result) {
//
//                    val members = i.get("members") as Array<*>
//                    var result: String = i.get("members").toString()
//                    val repeatedDays = i.get("repeatedDays") as Array<*>

                    val todayTaskData = Habits(
                        i.get("id").toString(),
                        i.get("ownerID").toString(),
                        i.get("members").toString(),
                        i.get("category").toString(),
                        i.get("task").toString(),
                        i.get("repeatedDays").toString(),
                        i.get("duration").toString(),
                        i.get("reminder").toString().toLong(),
                    )
                    Log.d("Cleooo", "$todayTaskData")

//                    Log.d("Cleooo", "${document.id} => ${document.data}")
//                    Log.d("Cleooo", "$newHa")
                    list.add(todayTaskData)
                }

                _todayTasks.value = list
            }
            .addOnFailureListener { exception ->
                Log.d("Cleooo", "get fail")
                Log.w("Cleooo", "Error getting documents.", exception)
            }
    }
}


