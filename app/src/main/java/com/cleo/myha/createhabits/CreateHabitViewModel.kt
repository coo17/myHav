package com.cleo.myha.createhabits

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.util.HABITS_PATH
import com.google.firebase.firestore.FirebaseFirestore

class CreateHabitViewModel : ViewModel() {

    private val firebase = FirebaseFirestore.getInstance()

    private val _habitCreated = MutableLiveData<List<Habits>>()
    val habitCreated: LiveData<List<Habits>>
        get() = _habitCreated

    private var _weeklyData = MutableLiveData<List<Boolean>>()
    val weeklyData: LiveData<List<Boolean>>
        get() = _weeklyData

    val list = mutableListOf<Boolean>(false, false, false, false, false, false, false)

    init {
        _weeklyData.value = list
    }

    fun selectDays(postion: Int) {
        list[postion] = !list[postion]
        _weeklyData.value = list
    }

    fun addData(
        email: String,
        category: String,
        duration: String,
        task: String,
        timer: String,
        reminder: Long,
        repeatedDays: List<Boolean>,
        createdTime: Long,
        startedDate: Long,
        endDate: Long,
        mode: Int
    ) {
        val articles = FirebaseFirestore.getInstance().collection(HABITS_PATH)
        val document = articles.document().id

            val dataOfHabit = Habits(
                document,
                email,
                listOf<String>(),
                category,
                task,
                repeatedDays,
                duration,
                reminder,
                timer,
                createdTime,
                startedDate,
                endDate,
                mode
            )

        firebase.collection(HABITS_PATH).document(document)
            .set(dataOfHabit).addOnSuccessListener {
                Log.d("Cleo", "Success!!")
            }
            .addOnFailureListener { e ->
                Log.d("Cleo", "add fail")
            }
    }
}
