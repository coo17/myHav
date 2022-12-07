package com.cleo.myha.habits.group

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class GroupViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var habits: Habits

    private val _groupHabits = MutableLiveData<List<Habits>>()
    val groupHabits: LiveData<List<Habits>>
        get() = _groupHabits

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    init {
        getGroupHabits()
    }

    private fun getGroupHabits() {

        user?.let { remoteUser ->
            db.collection("habits")
                .get()
                .addOnSuccessListener { documents ->
                    val list = documents.toObjects(Habits::class.java)

                    val habits = list.filter {
                        var today = Date().time

                        (it.mode == 1 && it.members!!.contains(remoteUser.email)) || (it.mode == 1 && it.ownerId == remoteUser.email) && (today < it.endDate)
                    }
                    _groupHabits.value = habits
                }
                .addOnFailureListener {
                    Log.d("Cleooo", "get fail")
                }
        }
    }

    fun setHabits(newHabits: Habits) {
        habits = newHabits
    }
}
