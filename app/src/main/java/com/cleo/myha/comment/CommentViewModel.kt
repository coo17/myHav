package com.cleo.myha.comment

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.CommentsInfo
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.cleo.myha.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class CommentViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var posts: Posts
    private lateinit var messges: CommentsInfo

    private var _userComments = MutableLiveData<List<CommentsInfo>>()
    val userComments: LiveData<List<CommentsInfo>>
        get() = _userComments

    private var _blockUserList = MutableLiveData<List<String>>()
    val blockUserList: LiveData<List<String>>
    get() = _blockUserList

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    init {
        getBlockList()
}


    fun getComments(blockLists: List<String>) {

        val articles = FirebaseFirestore.getInstance().collection("posts")
        val postId = posts.id

        articles.document(postId).collection("messages")
            .addSnapshotListener { value, error ->
                value?.let {
                val list = it.toObjects(CommentsInfo::class.java)

                _userComments.value = list.filter {
                    !blockLists.contains(it.senderId)
                }
                }

        }
    }

    fun getBlockList(){

        val email = user?.email.toString()

        db.collection("users").document(email)
            .get()
            .addOnSuccessListener {
                val bList = it.toObject(User::class.java)

                if (bList != null) {
                    _blockUserList.value = bList.blockLists
                }
            }
            .addOnFailureListener {

            }
    }

    fun setPosts(newPosts: Posts) {
        posts = newPosts
    }
}
