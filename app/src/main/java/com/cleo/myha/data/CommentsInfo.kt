package com.cleo.myha.data

import com.google.firebase.Timestamp

data class CommentsInfo(
    var senderId: String = "",
    var content: String = "",
    var createdTime: Timestamp? = null,
    var userName: String = ""
)
