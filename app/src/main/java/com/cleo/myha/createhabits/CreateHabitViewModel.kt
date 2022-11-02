package com.cleo.myha.createhabits

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleo.myha.data.Habits
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class CreateHabitViewModel : ViewModel() {

    private val firebase = FirebaseFirestore.getInstance()

    private val _habitCreated = MutableLiveData<List<Habits>>()
    val habitCreated: LiveData<List<Habits>>
        get() = _habitCreated

//
//    init {
//        addHabit()
//    }

//    fun addHabit() {
//        val habitCreated = FirebaseFirestore.getInstance().collection("habits")
//        Log.d("ddd","$habitCreated")
//        val document = habitCreated.document()
////        val data
////        document.set(data)
//    }

//    fun addHabit(category: String, id: String, duration: Int, task: String, repeatedDays: Boolean){
//
//        val userHabit: MutableMap<String, Any> = HashMap()
//        userHabit["category"] to category
//        userHabit["duration"] to duration
//        userHabit["id"] = "IU@gmail.com"
//        userHabit["members"] to
//        userHabit["ownerId"] to
//        userHabit["reminder"] to
//        userHabit["repeatedDays"] to repeatedDays
//        userHabit["task"] to task
//
//        firebase.collection("habits").add("id").addOnSuccessListener {
//            Log.d("Cleooo","Success!!")
//        }
//            .addOnFailureListener { e ->
//                Log.d("Cleooo", "add fail")
//            }
//    }

}