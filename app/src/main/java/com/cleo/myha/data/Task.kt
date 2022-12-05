package com.cleo.myha.data

import com.google.firebase.Timestamp

data class Task(
    var completedTime: String ="",
    var email: String ="",
    var id: String ="",
    var isDone: Boolean ? = null
)
