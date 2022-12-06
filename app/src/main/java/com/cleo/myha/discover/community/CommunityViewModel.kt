package com.cleo.myha.discover.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CommunityViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _groupTasks = MutableLiveData<List<Habits>>()
    val groupTasks: LiveData<List<Habits>>
        get() = _groupTasks

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    init {
        getGroupTasks()
    }

    private fun getGroupTasks() {

        db.collection("habits")
            .get()
            .addOnSuccessListener { documents ->
                val list = documents.toObjects(Habits::class.java)

                Log.d("VIC", "${documents.size()}")

                val joinHabits = list.filter {
                    it.mode == 1
                }
                _groupTasks.value = joinHabits

            }
            .addOnFailureListener {
                Log.d("Cleooo", "get fail")
            }
    }

}