package com.cleo.myha.createhabits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
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
}
