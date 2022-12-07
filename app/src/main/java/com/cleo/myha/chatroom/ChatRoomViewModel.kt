package com.cleo.myha.chatroom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.*
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatRoomViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var habit: Habits
    private lateinit var users: User

    private var _userData = MutableLiveData<List<Habits>>()
    val userData: LiveData<List<Habits>>
        get() = _userData

    private var _userInfo = MutableLiveData<List<User>>()
    val userInfo: LiveData<List<User>>
        get() = _userInfo

    private var _sender = MutableLiveData<List<UserType>>()
    val sender: LiveData<List<UserType>>
        get() = _sender

    var textInput = ""

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    init {
    }

    private fun getUserData() {

        val habitID = habit.id

        user?.let { remoteUser ->
            db.collection("habits").document(habitID)
                .get()
                .addOnSuccessListener { documents ->

                    val uData = documents.toObject(Habits::class.java)

                    val uList = mutableListOf<String>()

                    uList.addAll(uData?.members as List)
                    uList.add(uData.ownerId)
                    val userLists = mutableListOf<User>()

                    for (email in uList) {

                        db.collection("users").document(email)
                            .get()
                            .addOnSuccessListener {
                                val uAllData = it.toObject(User::class.java)

                                Log.d("CIV", "get $userLists")

                                uAllData?.let { User ->
                                    userLists.add(User)

                                    Log.d("CIV", "oh $userLists")
                                }

                                _userInfo.value = userLists
                            }.addOnFailureListener {
                            }
                    }
                }
                .addOnFailureListener {
                    Log.d("Cleooo", "get fail")
                }
        }
    }

    private fun getReceivedMessage() {

        val habitID = habit.id

        db.collection("habits").document(habitID)
            .collection("messages")
            .addSnapshotListener { value, error ->
                value?.let {

                    val list = it.toObjects(MessagesInfo::class.java) as List<MessagesInfo>
                    val sortList = list.sortedBy {
                        it.textTime
                    }

                    val myList = mutableListOf<UserType>()
                    for (item in sortList) {
                        Log.d("MAGIC", "$item")

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

        val senderId = auth.currentUser?.let {
            it.email
        }

        val data = MessagesInfo(
            senderEmail = senderId.toString(),
            senderName = auth.currentUser?.let {
                it.displayName
            }!!,
            senderImage = auth.currentUser?.photoUrl.toString(),
            message = textInput,
            textTime = Timestamp.now()
        )

        Log.d("C", "comment $senderId")

        db.collection("habits")
            .document(habit.id)
            .collection("messages")
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
