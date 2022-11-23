package com.cleo.myha.chatroom


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class ChatRoomViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var habit: Habits

    private var _userMessage = MutableLiveData<List<MessagesInfo>>()
    val userMessage: LiveData<List<MessagesInfo>>
        get() = _userMessage

    private var _sender = MutableLiveData<List<UserType>>()
    val sender: LiveData<List<UserType>>
        get() = _sender

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    init {

    }

    private fun getReceivedMessage() {

        val habitID = habit.id

        db.collection("habits").document(habitID)
            .collection("messages")
            .addSnapshotListener { value, error ->
                value?.let {

                    val list = it.toObjects(MessagesInfo::class.java) as List<MessagesInfo>
                    val sortList= list.sortedBy{
                        it.textTime
                    }

                    val myList = mutableListOf<UserType>()
                    for(item in sortList){
                        Log.d("MAGIC", "$item")

                        val message = if(item.senderEmail == auth.currentUser?.email){
                            UserType.Sender(item)
                        }else{
                            UserType.Receiver(item)
                        }
                        myList.add(message)


                    }

//                    val myMessage = list.filter {
//                        it.senderEmail == auth.currentUser?.email
//                    }.map {
//                        UserType.Sender(it)
//                    }
//
//
//                    val otherMessage = list.filter {
//                        it.senderEmail != auth.currentUser?.email
//                    }.map {
//                        UserType.Receiver(it)
//                    }
//
//
//
//                    myList.addAll(myMessage)
//                    myList.addAll(otherMessage)

                    _sender.value = myList
                }
            }
    }


    fun setHabits(newHabit: Habits) {
        habit = newHabit
        getReceivedMessage()
    }
}
