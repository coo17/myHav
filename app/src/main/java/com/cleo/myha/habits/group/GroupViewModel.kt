package com.cleo.myha.habits.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.firestore.FirebaseFirestore

class GroupViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _groupHabits = MutableLiveData<List<Habits>>()
    val groupHabits: LiveData<List<Habits>>
        get() = _groupHabits

    init {
       getGroupTasks()
    }

    private fun getGroupTasks() {

        db.collection("habits")
            .get()
            .addOnSuccessListener { documents ->
                val list = documents.toObjects(Habits::class.java)

                Log.d("VIC","${documents.size()}")


                val singleHabits = list.filter {
                    it.mode == 1
                }
                _groupHabits.value = singleHabits

            }
            .addOnFailureListener {
                Log.d("Cleooo", "get fail")}
    }

}