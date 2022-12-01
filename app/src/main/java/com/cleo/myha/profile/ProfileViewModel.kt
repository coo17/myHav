package com.cleo.myha.profile


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import convertDurationToCertain
import convertDurationToDate
import java.time.Instant
import java.time.LocalDate
import java.time.Year
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.roundToInt


class ProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    //    private lateinit var auth: FirebaseAuth

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    //    private lateinit var habit: Habits
    private var myHabit: Habits? = null

    private var _myPosts = MutableLiveData<List<Posts>>()
    val myPosts: LiveData<List<Posts>>
        get() = _myPosts

    private var _allHabits = MutableLiveData<List<Habits>>()
    val allHabits: LiveData<List<Habits>>
        get() = _allHabits

    private var _allTasks = MutableLiveData<Float>()
    val allTasks: LiveData<Float>
        get() = _allTasks

    private var _completedTasks = MutableLiveData<Float>()
    val completedTasks: LiveData<Float>
        get() = _completedTasks

    private var _percentTask = MutableLiveData<Float>()
    val percentTask: LiveData<Float>
        get() = _percentTask

    private var _donutSet= MutableLiveData<List<Float>>()
    val donutSet: LiveData<List<Float>>
        get() = _donutSet

    init {
        getAllHabits()
    }

    private fun getAllHabits() {


        user?.let {
            db.collection("habits")
                .whereEqualTo("ownerId", it.email)
                .get()
                .addOnSuccessListener { documents ->
                    val list = documents.toObjects(Habits::class.java)

                    //計算所有tasks
                    var totalTaks = 0
                    for (result in list) {

                        val habitStartedDay = result.startedDate.convertDurationToCertain()
                        val habitEndDay = result.endDate.convertDurationToCertain()

                        totalTaks += calcWeekDay(habitStartedDay, habitEndDay, result.repeatedDays)
                    }

                    //task isDone == true 總數量
                    var habitCompleted = 0
                    for (hId in list) {
                        db.collection("habits")
                            .document(hId.id)
                            .collection("Test")
                            .addSnapshotListener { value, _ ->

                                value?.let { snapshot ->

                                    val taskList = snapshot.toObjects(Task::class.java)
                                    val sortList = taskList.sortedBy { sOkay ->
                                        sOkay.isDone == true
                                    }.size

                                    habitCompleted += sortList
                                    _completedTasks.value = habitCompleted.toFloat()
                                    val percentage = (habitCompleted.toFloat()/totalTaks.toFloat()) * 100

                                    _percentTask.value = percentage

                                    val uncompleted = totalTaks.minus(habitCompleted)
                                    Log.d("MFK","this is $uncompleted")
                                    val donutList = listOf(
                                        percentage,
                                        100F


                                    )
                                    Log.d("MFK","this is $donutList")


                                    _donutSet.value = donutList
                                }
                            }
                    }

                    _allHabits.value = list
                    _allTasks.value = totalTaks.toFloat()

                }
                .addOnFailureListener {
                    Log.d("Cleooo", "get fail")
                }
        }
    }

    fun calcWeekDay(startDay: String, endDay: String, repeatedDays: List<Boolean>): Int {

        val sDayYear = startDay.split(",")[2].toInt()
        val sDayMonth = startDay.split(",")[0].toInt() - 1
        val sDay = startDay.split(",")[1].toInt()

        val eDayYear = endDay.split(",")[2].toInt()
        val eDayMonth = endDay.split(",")[0].toInt() - 1
        val eDay = endDay.split(",")[1].toInt()

        val startCal = Calendar.getInstance()
        startCal.set(sDayYear, sDayMonth, sDay)

        val endCal = Calendar.getInstance()
        endCal.set(eDayYear, eDayMonth, eDay)

        val totalCal = Calendar.getInstance()
        totalCal.time = startCal.time

        //計算task==weekdays==true
        var taskDay = 0
        while (totalCal.toInstant().epochSecond <= endCal.toInstant().epochSecond) {
            if (totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && repeatedDays[0] == true) {
                taskDay += 1
            }
            if (totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && repeatedDays[1] == true) {
                taskDay += 1
            }
            if (totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && repeatedDays[2] == true) {
                taskDay += 1
            }
            if (totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && repeatedDays[3] == true) {
                taskDay += 1
            }
            if (totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && repeatedDays[4] == true) {
                taskDay += 1
            }
            if (totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && repeatedDays[5] == true) {
                taskDay += 1
            }
            if (totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && repeatedDays[6] == true) {
                taskDay += 1
            }
            totalCal.add(Calendar.DATE, 1)
        }

        return taskDay

    }
}
