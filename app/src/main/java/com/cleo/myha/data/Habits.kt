package com.cleo.myha.data

import android.util.Log

data class Habits(
    var id: String,
    var ownerId: String,
    var members: List<String>,
    var category: String,
    var task: String,
    var repeatedDays: List<Boolean>,
    var duration: String,
    var reminder: Long,
    var timer:String,
    var createdTime: Long,
    var startedDate: Long,
    var endDate: Long
//    var pomodoro: List<PomodoroData>
)


data class PomodoroData(
    var startTime: Long,
    var stopTime: Long,
    var resetTime: Long,
    var saveTime: Long
)