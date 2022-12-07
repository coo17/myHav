package com.cleo.myha.data

sealed class UserType {

    abstract val id: String

    data class Sender(val user: MessagesInfo) : UserType() {
        override val id: String
            get() = user.senderEmail
    }

    data class Receiver(val user: MessagesInfo) : UserType() {
        override val id: String
            get() = user.senderEmail
    }
}
