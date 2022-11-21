package com.cleo.myha.data

data class ChatRooms(
    var roomId: String ="",
    var habitId: String= "",
    var members: Array<String> ? = null,
    var ownerId: String = "",
    var messages: List<MessagesInfo> ? = null
)

data class MessagesInfo(
    var senderId: String = "",
    var text: String = "",
    var time: Long = -1L
)
