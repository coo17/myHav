package com.cleo.myha.data

data class Habits(
    var id: String,
    var ownerId: String,
//    var members: Array<String>,
    var members: String,
    var category: String,
    var task: String,
//    var repeatedDays: Array<Boolean>,
    var repeatedDays: String,
    var duration: String,
    var reminder: Long,
//    var pomodoro: List<PomodoroData>
)


data class PomodoroData(
    var startTime: Long,
    var stopTime: Long,
    var resetTime: Long,
    var saveTime: Long
)