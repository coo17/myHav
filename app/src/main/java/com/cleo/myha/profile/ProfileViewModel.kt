package com.cleo.myha.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import convertDurationToCertain
import java.util.*
import kotlin.collections.List

class ProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    //    private lateinit var auth: FirebaseAuth

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    //    private lateinit var habit: Habits
    private var myHabit: Habits? = null
    private var userList: User ? = null

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

    private var _donutSet = MutableLiveData<List<Float>>()
    val donutSet: LiveData<List<Float>>
        get() = _donutSet

    private var _userSingleHabit = MutableLiveData<List<Habits>>()
    val userSinglerHabit: LiveData<List<Habits>>
        get() = _userSingleHabit

    private var _userGroupHabit = MutableLiveData<List<List<Habits>>>()
    val userGroupHabit: LiveData<List<List<Habits>>>
        get() = _userGroupHabit

    private var _categoryList = MutableLiveData<List<HabitTracker>>()
    val categoryList: LiveData<List<HabitTracker>>
        get() = _categoryList

    init {
        getAllHabits()
        getUserHabits()
    }

    private fun getAllHabits() {

        user?.let {
            db.collection("habits")
                .whereEqualTo("ownerId", it.email)
                .get()
                .addOnSuccessListener { documents ->
                    val list = documents.toObjects(Habits::class.java)

                    // 計算所有tasks
                    var totalTaks = 0
                    for (result in list) {

                        val habitStartedDay = result.startedDate.convertDurationToCertain()
                        val habitEndDay = result.endDate.convertDurationToCertain()

                        totalTaks += calcWeekDay(habitStartedDay, habitEndDay, result.repeatedDays)
                    }

                    // task isDone == true 總數量
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
                                    val percentage =
                                        (habitCompleted.toFloat() / totalTaks.toFloat()) * 100

                                    _percentTask.value = percentage

                                    val uncompleted = totalTaks.minus(habitCompleted)
                                    Log.d("MFK", "this is $uncompleted")
                                    val donutList = listOf(
                                        percentage,
                                        100F

                                    )
                                    Log.d("MFK", "this is $donutList")

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

        // 計算task==weekdays==true
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

    private fun getHabitCategory(list: List<Habits>, email: String) {

        val habitTracker = mutableListOf<HabitTracker>()

        val healthList = list.filter {
            (it.category == "Health") && (
                (it.mode == 0 && it.ownerId == email) || (
                    it.mode == 1 && it.members!!.contains(
                        email
                    )
                    ) || (it.mode == 1 && it.ownerId == email)
                )
        }.size

        val workoutList = list.filter {
            (it.category == "Workout") && (
                (it.mode == 0 && it.ownerId == email) || (
                    it.mode == 1 && it.members!!.contains(
                        email
                    )
                    ) || (it.mode == 1 && it.ownerId == email)
                )
        }.size

        val readingList = list.filter {
            (it.category == "Reading") && (
                (it.mode == 0 && it.ownerId == email) || (
                    it.mode == 1 && it.members!!.contains(
                        email
                    )
                    ) || (it.mode == 1 && it.ownerId == email)
                )
        }.size

        val learningList = list.filter {
            (it.category == "Learning") && (
                (it.mode == 0 && it.ownerId == email) || (
                    it.mode == 1 && it.members!!.contains(
                        email
                    )
                    ) || (it.mode == 1 && it.ownerId == email)
                )
        }.size

        val generalList = list.filter {
            (it.category == "General") && (
                (it.mode == 0 && it.ownerId == email) || (
                    it.mode == 1 && it.members!!.contains(
                        email
                    )
                    ) || (it.mode == 1 && it.ownerId == email)
                )
        }.size

        habitTracker.add(HabitTracker("Health", healthList))
        habitTracker.add(HabitTracker("Workout", workoutList))
        habitTracker.add(HabitTracker("Reading", readingList))
        habitTracker.add(HabitTracker("Learning", learningList))
        habitTracker.add(HabitTracker("General", generalList))

        _categoryList.value = habitTracker
    }

    private fun getUserHabits() {

        user?.let { remoteUser ->
            db.collection("habits")
                .get()
                .addOnSuccessListener { documents ->
                    val list = documents.toObjects(Habits::class.java)

                    remoteUser.email?.let {
                        getHabitCategory(list, it)
                    }
                    Log.d("JOMALONE", "Size: ${documents.size()}")

//                    val healthList = list.filter {
//                        (it.category == "Health") && ((it.mode == 0 && it.ownerId == remoteUser.email) || (it.mode == 1 && it.members!!.contains(
//                            remoteUser.email
//                        )) || (it.mode == 1 && it.ownerId == remoteUser.email))
//                    }
//
//                    Log.d("JOMALONE", "Health: $healthList")
//
//                    val workoutList = list.filter {
//                        (it.category == "Workout") && ((it.mode == 0 && it.ownerId == remoteUser.email) || (it.mode == 1 && it.members!!.contains(
//                            remoteUser.email
//                        )) || (it.mode == 1 && it.ownerId == remoteUser.email))
//                    }
//
//                    val readingList = list.filter {
//                        (it.category == "Reading") && ((it.mode == 0 && it.ownerId == remoteUser.email) || (it.mode == 1 && it.members!!.contains(
//                            remoteUser.email
//                        )) || (it.mode == 1 && it.ownerId == remoteUser.email))
//                    }
//
//                    val learningList = list.filter {
//                        (it.category == "Learning") && ((it.mode == 0 && it.ownerId == remoteUser.email) || (it.mode == 1 && it.members!!.contains(
//                            remoteUser.email
//                        )) || (it.mode == 1 && it.ownerId == remoteUser.email))
//                    }
//
//                    val generalList = list.filter {
//                        (it.category == "General") && ((it.mode == 0 && it.ownerId == remoteUser.email) || (it.mode == 1 && it.members!!.contains(
//                            remoteUser.email
//                        )) || (it.mode == 1 && it.ownerId == remoteUser.email))
//                    }
//
//                    val allLists = mutableListOf<List<Habits>>()
//                    allLists.add(healthList)
//                    allLists.add(workoutList)
//                    allLists.add(readingList)
//                    allLists.add(learningList)
//                    allLists.add(generalList)
//
//                    _userGroupHabit.value = allLists
                }.addOnFailureListener {
                    Log.d("Cleooo", "get fail")
                }
        }
    }
}
