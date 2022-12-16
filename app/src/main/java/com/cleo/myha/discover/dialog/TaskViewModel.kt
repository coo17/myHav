package com.cleo.myha.discover.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class TaskViewModel : ViewModel() {

    private lateinit var habits: Habits

    private var _joinCardToMine = MutableLiveData<List<Habits>>()
    val joinCardToMine: LiveData<List<Habits>>
        get() = _joinCardToMine


    private fun addCard() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val db = FirebaseFirestore.getInstance().collection("habits")
        val habitId = habits.id
        val mId = user?.email

        db.document(habitId)
            .update("members", FieldValue.arrayUnion(mId))
            .addOnSuccessListener {

                Log.d("Cleo", "get success ,$it")
                Log.d("TAG", "add success")
            }
            .addOnFailureListener {
                Log.d("2022", "add fail")
                Log.w("2022", "Error adding document", it)
            }
    }

    fun setHabitCard(newCard: Habits){
        habits = newCard
        addCard()
    }
}
