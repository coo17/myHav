package com.cleo.myha.comment.blocklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.User
import com.google.firebase.firestore.FirebaseFirestore

class BlockViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()
//    private var auth = FirebaseAuth.getInstance()
//    private var user = auth.currentUser

    private var _allUser = MutableLiveData<List<User>>()
    val allUser: LiveData<List<User>>
        get() = _allUser

    init {
        addNewBlockUser()
    }

    private fun addNewBlockUser() {
    }
}
