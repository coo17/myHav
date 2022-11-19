package com.cleo.myha.habits.single

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cleo.myha.data.Category
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class SingleViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _yourHabits = MutableLiveData<List<Habits>>()
    val yourHabits: LiveData<List<Habits>>
        get() = _yourHabits

    init {
        getSingleHabits()
    }

   private fun getSingleHabits(){

        db.collection("habits")
            .get()
            .addOnSuccessListener { documents ->
                val list = documents.toObjects(Habits::class.java)

                Log.d("VIC","${documents.size()}")


               val singleHabits = list.filter {
                    it.mode == 0
                }
                _yourHabits.value = singleHabits

            }
            .addOnFailureListener {
                Log.d("Cleooo", "get fail")}
    }

}