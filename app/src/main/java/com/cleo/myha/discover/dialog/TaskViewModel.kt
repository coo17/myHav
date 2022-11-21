package com.cleo.myha.discover.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class TaskViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var habits: Habits

    private val _joinCardToMine = MutableLiveData<List<Habits>>()
    val joinCardToMine: LiveData<List<Habits>>
        get() = _joinCardToMine


    init {

    }

//    fun addCard() {
//
//        val documentId = habits.id
//        val mId = "cleo@gmail.com"
//
//        db.collection("habits")
//            .document(documentId)
//            .update("members", FieldValue.arrayUnion(mId))
//            .addOnSuccessListener {
//                Log.d("2022", "add success")
//            }
//            .addOnFailureListener {
//                Log.d("2022", "add fail")
//                Log.w("2022", "Error adding document", it)
//            }
//
//    }
}