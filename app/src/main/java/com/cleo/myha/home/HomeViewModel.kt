package com.cleo.myha.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.Instant
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

    var completedList = mutableMapOf<String, Boolean>()

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val userEmail = user?.email.toString()

    init {
        getTodayTasks()
    }

    fun queryTest(habitId: String) {
//        val habitId = "30l3lmRirwifxWmrydox"
//        val todayString = "2022-11-10"
        val todayString = Date().time
        val date = convertLongToTime(todayString)

        db.collection("habits")
            .document(habitId)
            .collection("Test")
            .document(date)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("Cleoo","count: ${it.result?.data?.size}")
                    it.result?.data?.let {
                        (it["isDone"] as Boolean?)?.let {
                           completedList.put(habitId, it)
                            _doneList.value = completedList
                            Log.d("VIC","isDone: ${completedList}")
                            Log.d("VICC","isDone: $it")
                        }
                        Log.d("Cleoo","isDone: ${it["isDone"]}")
                    }
                } else {
                    Log.d("Cleoo","fail")
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
                Log.d("Cleoo","${result.size()}")

                for (i in result) {
                    Log.d("Cleoo","id=${i.get("id").toString()}")

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
                    Log.d("OMG","${ i.get("endDate").toString().toLong()}")
                    list.add(todayTaskData)
                }

                Log.d("Cleoo","${list.size}")
                var today = Date().time
                var dayOfWeek = Instant.ofEpochMilli(today).atZone(ZoneId.systemDefault()).toLocalDate().dayOfWeek.value
                Log.d("VICC", "cleooo ${dayOfWeek}")

                val toDoList =  list.filter{
                    today > it.startedDate && today < it.endDate && it.repeatedDays.get(dayOfWeek-1) == true
                }
                Log.d("kkk", "${toDoList}")
//                kkk.sortBy { it.reminder }
                _todayTasks.value = toDoList
            }
            .addOnFailureListener { exception ->
                Log.d("Cleooo", "get fail")
                Log.w("Cleooo", "Error getting documents.", exception)
            }
    }


    fun convertLongToTime(time: Long): String{
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-d", Locale.getDefault())
        return format.format(date)
    }


    fun sendCompletedTask(data: Habits, isDone: Boolean){

        val document = data.id
        val userEmail = user?.email.toString()
        val nowTime = Date().time
        val date = convertLongToTime(nowTime)
//        val taskState : Boolean = isDone ?: false

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
                Log.d("Cleooo","Success! ${nowTime}")
            }
            .addOnFailureListener {
                Log.d("Cleooo", "oh, it's fail")
            }

        completedList.set(data.id, isDone)
    }

    fun checkAllList():Boolean {

//       var maxValue =0
        //filter出一樣的size
        return completedList.filter {
            it.value == true
        }.size == completedList.size
    }

}



