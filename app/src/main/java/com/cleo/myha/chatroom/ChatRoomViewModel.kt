package com.cleo.myha.chatroom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.*
import com.cleo.myha.util.HABITS_PATH
import com.cleo.myha.util.MESSAGES_SUB_HABITS
import com.cleo.myha.util.USERS_PATH
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatRoomViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var habit: Habits

    private var _userInfo = MutableLiveData<List<User>>()
    val userInfo: LiveData<List<User>>
        get() = _userInfo

    private var _sender = MutableLiveData<List<UserType>>()
    val sender: LiveData<List<UserType>>
        get() = _sender

    var textInput = ""

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    private fun getUserData() {

        val habitID = habit.id

        user?.let { _ ->
            db.collection(HABITS_PATH).document(habitID)
                .get()
                .addOnSuccessListener { documents ->

                    val uData = documents.toObject(Habits::class.java)

                    val uList = mutableListOf<String>()

                    uList.addAll(uData?.members as List)
                    uList.add(uData.ownerId)
                    val userLists = mutableListOf<User>()

                    for (email in uList) {

                        db.collection(USERS_PATH).document(email)
                            .get()
                            .addOnSuccessListener {
                                val uAllData = it.toObject(User::class.java)

                                Log.d("Cleo", "get $userLists")

                                uAllData?.let { User ->
                                    userLists.add(User)

                                    Log.d("Cleo", "oh $userLists")
                                }

                                _userInfo.value = userLists
                            }.addOnFailureListener {


                            }
                    }
                }
                .addOnFailureListener {
                    Log.d("Cleo", "get fail")
                }
        }
    }

    private fun getReceivedMessage() {

        val habitID = habit.id

        db.collection(HABITS_PATH).document(habitID)
            .collection(MESSAGES_SUB_HABITS)
            .addSnapshotListener { value, _ ->
                value?.let {

                    val list = it.toObjects(MessagesInfo::class.java) as List<MessagesInfo>
                    val sortList = list.sortedBy { messageInfo ->
                        messageInfo.textTime
                    }

                    val myList = mutableListOf<UserType>()
                    for (item in sortList) {
                        Log.d("Message", "$item")

                        val message = if (item.senderEmail == auth.currentUser?.email) {
                            UserType.Sender(item)
                        } else {
                            UserType.Receiver(item)
                        }
                        myList.add(message)
                    }
                    _sender.value = myList
                }
            }
    }

    private fun addComment() {

        val senderId = auth.currentUser?.email

        val data = MessagesInfo(
            senderEmail = senderId.toString(),
            senderName = auth.currentUser?.displayName!!,
            senderImage = auth.currentUser?.photoUrl.toString(),
            message = textInput,
            textTime = Timestamp.now()
        )

        Log.d("C", "comment $senderId")

        db.collection(HABITS_PATH)
            .document(habit.id)
            .collection(MESSAGES_SUB_HABITS)
            .add(data)
            .addOnSuccessListener {

                Log.d("Cleo", "Get Comment Success!!")
            }
            .addOnFailureListener { e ->
                Log.d("Cleo", "add fail")
            }
    }

    fun setHabits(newHabit: Habits) {
        habit = newHabit
        getReceivedMessage()
        getUserData()
    }

    fun sendTextInput(content: String) {
        textInput = content
        addComment()
    }
}
