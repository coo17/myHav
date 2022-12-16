package com.cleo.myha.discover.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.util.GROUP_MODE
import com.cleo.myha.util.HABITS_PATH
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

        db.collection(HABITS_PATH)
            .get()
            .addOnSuccessListener { documents ->
                val list = documents.toObjects(Habits::class.java)

                Log.d("Cleo", "${documents.size()}")

                val joinHabits = list.filter {
                    it.mode == GROUP_MODE
                }
                _groupTasks.value = joinHabits
            }
            .addOnFailureListener {
                Log.d("Cleooo", "get fail")
            }
    }
}
