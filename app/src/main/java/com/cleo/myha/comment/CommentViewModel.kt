package com.cleo.myha.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.CommentsInfo
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class CommentViewModel : ViewModel() {

    private var firebase = FirebaseFirestore.getInstance()
    private lateinit var posts: Posts

    private val _userComments = MutableLiveData<List<CommentsInfo>>()
    val userComments: LiveData<List<CommentsInfo>>
        get() = _userComments

    init {
//        getComments()
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-d", Locale.getDefault())
        return format.format(date)
    }


    fun getComments() {
        val articles = FirebaseFirestore.getInstance().collection("posts")
        val postId = posts.id

        articles.document(postId).collection("messages")
            .addSnapshotListener { value, error ->
                value?.let {
                val list = it.toObjects(CommentsInfo::class.java)
                _userComments.value = list
                }

        }
    }

    fun setPosts(newPosts: Posts) {
        posts = newPosts
        getComments()
    }
}
