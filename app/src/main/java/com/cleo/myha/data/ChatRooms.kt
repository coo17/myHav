package com.cleo.myha.data

import com.google.firebase.Timestamp

data class ChatRooms(
    var roomId: String ="",
    var habitId: String= "",
    var members: Array<String> ? = null,
    var ownerId: String = "",
    var messages: List<MessagesInfo> ? = null
)

data class MessagesInfo(
    var senderEmail: String = "",
    var senderName: String = "",
    var senderImage: String = "",
    var message: String = "",
    var textTime: Timestamp? = null ,
)
