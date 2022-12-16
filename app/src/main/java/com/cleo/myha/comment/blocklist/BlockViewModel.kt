package com.cleo.myha.comment.blocklist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.User
import com.cleo.myha.util.BLOCKLIST_SUB_USERS
import com.cleo.myha.util.USERS_PATH
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class BlockViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    private var _navigateUp = MutableLiveData<Boolean>()
    val navigateUp: LiveData<Boolean>
        get() = _navigateUp

    var newEmail = ""


    private fun addNewBlockUser() {

        val senderId = FirebaseAuth.getInstance()
            .currentUser?.email.toString()

        db.collection(USERS_PATH)
            .document(senderId)
            .update(BLOCKLIST_SUB_USERS, FieldValue.arrayUnion(newEmail))
            .addOnSuccessListener {
                _navigateUp.value = true
                Log.d("Cleo", "You blocked it!")
            }.addOnFailureListener {
                Log.d("Cleo", "fail")
            }
    }

    fun setEmail(email: String) {
        newEmail = email
        addNewBlockUser()
    }
}
