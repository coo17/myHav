package com.cleo.myha.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import convertDurationToDate
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.util.*

class HomeViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private var _todayTasks = MutableLiveData<List<Habits>>()
    val todayTasks: LiveData<List<Habits>>
        get() = _todayTasks

    private var _completedTask = MutableLiveData<List<Habits>>()
    val completedTask: LiveData<List<Habits>>
        get() = _completedTask

    private var _doneList = MutableLiveData<Map<String, Boolean>>()
    val doneList: LiveData<Map<String, Boolean>>
        get() = _doneList

    private var _monthOfList = MutableLiveData<Map<Calendar, Boolean>>()
    val monthOfList: LiveData<Map<Calendar, Boolean>>
        get() = _monthOfList

    var completedList = mutableMapOf<String, Boolean>()

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    private val userEmail = user?.email.toString()

    var selectedDate: Long? = null

    init {
        getTodayTasks()
    }

    fun queryTest(habitId: String) {

        val todayString = Date().time
        val date = convertLongToTime(todayString)

        db.collection("habits")
            .document(habitId)
            .collection("Test")
            .document(date)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("Cleoo", "count: ${it.result?.data?.size}")
                    it.result?.data?.let {
                        (it["isDone"] as Boolean?)?.let {
                            completedList.put(habitId, it)
                            _doneList.value = completedList
                            Log.d("VIC", "isDone: ${completedList}")
                            Log.d("VICC", "isDone: $it")
                        }
                        Log.d("Cleoo", "isDone: ${it["isDone"]}")
                    }
                } else {
                    Log.d("Cleoo", "fail")
                }
            }
    }

    private fun getTodayTasks() {


        db.collection("habits")
            .whereEqualTo("ownerId", userEmail)
            .get()
            .addOnSuccessListener { result ->
//                Log.d("Cleooo", "get success ,${result.documents}")
                val list = mutableListOf<Habits>()
                Log.d("Cleoo", "${result.size()}")

                for (i in result) {
                    Log.d("Cleoo", "id=${i.get("id").toString()}")

                    val members = i.get("members") as List<String>
                    val repeatedDays = i.get("repeatedDays") as List<Boolean>

                    val todayTaskData = Habits(
                        i.get("id").toString(),
                        i.get("ownerID").toString(),
                        members,
                        i.get("category").toString(),
                        i.get("task").toString(),
                        repeatedDays,
                        i.get("duration").toString(),
                        i.get("reminder").toString().toLong(),
                        i.get("timer").toString(),
                        i.get("createdTime").toString().toLong(),
                        i.get("startedDate").toString().toLong(),
                        i.get("endDate").toString().toLong(),
                        i.get("mode").toString().toInt()
                    )
                    queryTest(todayTaskData.id)
                    list.add(todayTaskData)
                }

                getMonthDataList(list)

                val shownDate = if (selectedDate == null) {
                    Date().time
                } else {
                    selectedDate
                }

//                val dayOfWeek = Instant.ofEpochMilli(today).atZone(ZoneId.systemDefault()).toLocalDate().dayOfWeek.value
                val dayOfWeek = Instant.ofEpochSecond(shownDate!!).atZone(ZoneId.systemDefault())
                    .toLocalDate().dayOfWeek.value
                val toDoList = list.filter {
                    shownDate > it.startedDate && shownDate < it.endDate && it.repeatedDays.get(
                        dayOfWeek - 1
                    ) == true
                }
                Log.d("kkk", "${toDoList.size}")

                _todayTasks.value = toDoList
            }
            .addOnFailureListener { exception ->
                Log.d("Cleooo", "get fail")
                Log.w("Cleooo", "Error getting documents.", exception)
            }
    }

    private fun getMonthDataList(list: MutableList<Habits>) {

        val month = LocalDate.now().month
        val year = LocalDate.now().year
//        val firstDateOfMonth = LocalDate.of(year, month, 1)
//        Log.d("JOMALONE", "$dayOfWeeks")
        val firstDayOfMonth = Calendar.getInstance()
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)

        val today = Date()
        val calendar = Calendar.getInstance()

        calendar.add(Calendar.MONTH, 1)
        calendar[Calendar.DAY_OF_MONTH] = 1
        calendar.add(Calendar.DATE, -1)
        val lastDayOfMonth = calendar.time
        val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd")

        Log.d("JOMALONE", "Today is ${sdf.format(today)}")
        Log.d("JOMALONE", "First Day of Month ${firstDayOfMonth.time.time.convertDurationToDate()}")
        Log.d("JOMALONE", "Last Day of Month ${sdf.format(lastDayOfMonth)}")


        val totalCal = Calendar.getInstance()
        totalCal.time = firstDayOfMonth.time

        val monthList = mutableMapOf<Calendar, Boolean>()
        Log.d("X哥", "b${monthList.values}")
        while (totalCal.toInstant().epochSecond <= lastDayOfMonth.toInstant().epochSecond) {
//        while (totalCal.time.time <= lastDayOfMonth.time) {
            val dayOfWeek =
                Instant.ofEpochSecond(totalCal.timeInMillis).atZone(ZoneId.systemDefault())
                    .toLocalDate().dayOfWeek.value

            val toDoList = list.filter {
                totalCal.time.time > it.startedDate && totalCal.time.time <= it.endDate && it.repeatedDays.get(
                    dayOfWeek - 1
                ) == true
            }

            if (toDoList.isNotEmpty()) {

                monthList.put(totalCal, true)

            } else {
                monthList.put(totalCal, false)
            }

            totalCal.add(Calendar.DATE, 1)

        }

        _monthOfList.value = monthList
    }


    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-d", Locale.getDefault())
        return format.format(date)
    }

    fun sendCompletedTask(data: Habits, isDone: Boolean) {

        val document = data.id
        val userEmail = user?.email.toString()
        val nowTime = Date().time
        val date = convertLongToTime(nowTime)

        val dailyTask: MutableMap<String, Any> = HashMap()
        dailyTask["email"] = userEmail
        dailyTask["id"] = data.id
        dailyTask["completedTime"] = date
        dailyTask["isDone"] = isDone

        db.collection("habits")
            .document(document)
            .collection("Test")
            .document(date)
            .set(dailyTask)
            .addOnSuccessListener {
                Log.d("Cleooo", "Success! ${nowTime}")
            }
            .addOnFailureListener {
                Log.d("Cleooo", "oh, it's fail")
            }

        completedList.set(data.id, isDone)
    }

    fun checkAllList(): Boolean {

        return completedList.filter {
            it.value == true
        }.size == completedList.size
    }

    fun setDate(dateClicked: Date) {
        selectedDate = dateClicked.toInstant().toEpochMilli()
        getTodayTasks()
    }

}



