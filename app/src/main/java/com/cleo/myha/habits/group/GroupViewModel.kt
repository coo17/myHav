package com.cleo.myha.habits.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.google.firebase.firestore.FirebaseFirestore

class GroupViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var habits: Habits

    private val _groupHabits = MutableLiveData<List<Habits>>()
    val groupHabits: LiveData<List<Habits>>
        get() = _groupHabits

    init {
        getGroupHabits()
    }

    private fun getGroupHabits() {


        val userId = "Cleo@gmail.com"

        db.collection("habits")
            .get()
            .addOnSuccessListener { documents ->
                val list = documents.toObjects(Habits::class.java)

                Log.d("VIC","${documents.size()}")


                val habits = list.filter {
                    it.mode == 1
                    it.members!!.contains(userId)
                }
                _groupHabits.value = habits

            }
            .addOnFailureListener {
                Log.d("Cleooo", "get fail")}
    }

    fun setHabits(newHabits: Habits) {
        habits = newHabits
    }
}