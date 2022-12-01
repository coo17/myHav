package com.cleo.myha.profile


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
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


class ProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    //    private lateinit var auth: FirebaseAuth

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

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
                    Log.d("AESOP", "${documents.size()}")

                    var totalTaks = 0
                    for (result in list) {

                        //得到habit區間天數
                        val habitStartedDay = result.startedDate.convertDurationToCertain()
                        val habitEndDay = result.endDate.convertDurationToCertain()

                        totalTaks += calcWeekDay(habitStartedDay,habitEndDay, result.repeatedDays)
                        Log.d("MFKK", "${calcWeekDay(habitStartedDay,habitEndDay, result.repeatedDays)}")
                    }


                    _allHabits.value = list
                    _allTasks.value = totalTaks.toFloat()
                }
                .addOnFailureListener {
                    Log.d("Cleooo", "get fail")
                }
        }
    }

    fun calcWeekDay(startDay: String, endDay: String, repeatedDays: List<Boolean>): Int{

        val sDayYear= startDay.split(",")[2].toInt()
        val sDayMonth = startDay.split(",")[0].toInt()-1
        val sDay = startDay.split(",")[1].toInt()

        val eDayYear = endDay.split(",")[2].toInt()
        val eDayMonth = endDay.split(",")[0].toInt()-1
        val eDay = endDay.split(",")[1].toInt()

        val startCal = Calendar.getInstance()
        startCal.set(sDayYear,sDayMonth,sDay)

        val endCal = Calendar.getInstance()
        endCal.set(eDayYear,eDayMonth,eDay)

        val totalCal = Calendar.getInstance()
        totalCal.time = startCal.time

        //計算task==weekdays==true
        var taskDay = 0
        while(totalCal.toInstant().epochSecond <= endCal.toInstant().epochSecond){
            if(totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && repeatedDays[0] == true){
                taskDay += 1
            }
            if(totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && repeatedDays[1] == true){
                taskDay += 1
            }
            if(totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && repeatedDays[2] == true){
                taskDay += 1
            }
            if(totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && repeatedDays[3] == true) {
                taskDay += 1
            }
            if(totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && repeatedDays[4] == true) {
                taskDay += 1
            }
            if(totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && repeatedDays[5] == true){
                taskDay += 1
            }
            if(totalCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && repeatedDays[6] == true){
                taskDay += 1
            }
            totalCal.add(Calendar.DATE,1)
        }

        return taskDay

    }
}
