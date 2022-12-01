package com.cleo.myha.data

import com.google.firebase.Timestamp

data class MessagesInfo(
    var senderEmail: String = "",
    var senderName: String = "",
    var senderImage: String = "",
    var message: String = "",
    var textTime: Timestamp? = null,
)
