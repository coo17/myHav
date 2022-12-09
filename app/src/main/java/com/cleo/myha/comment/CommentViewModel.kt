package com.cleo.myha.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.CommentsInfo
import com.cleo.myha.data.Posts
import com.cleo.myha.data.User
import com.cleo.myha.util.MESSAGES_SUB_POSTS
import com.cleo.myha.util.POSTS_PATH
import com.cleo.myha.util.USERS_PATH
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CommentViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var posts: Posts

    private var _userComments = MutableLiveData<List<CommentsInfo>>()
    val userComments: LiveData<List<CommentsInfo>>
        get() = _userComments

    private var _blockUserList = MutableLiveData<List<String>>()
    val blockUserList: LiveData<List<String>>
        get() = _blockUserList

    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    var commentInput = ""

    init {
        getBlockList()
    }

    fun getComments(blockLists: List<String>) {

        val articles = FirebaseFirestore.getInstance().collection(POSTS_PATH)
        val postId = posts.id

        articles.document(postId).collection(MESSAGES_SUB_POSTS)
            .addSnapshotListener { value, _ ->
                value?.let {
                    val list = it.toObjects(CommentsInfo::class.java)

                    val commentList = list.sortedBy { ok ->
                        ok.createdTime
                    }
                    Log.d("Cleo", "${commentList.size}")

                    for(item in commentList){
                        val blockList = commentList.filter { noBlocklist ->
                            !blockLists.contains(noBlocklist.senderId)

                        }
                        Log.d("VIC", "${blockList.size}")
                        _userComments.value = blockList
                    }
                }
            }
    }

    private fun getBlockList() {

        val email = user?.email.toString()

        db.collection(USERS_PATH).document(email)
            .get()
            .addOnSuccessListener {
                val bList = it.toObject(User::class.java)

                if (bList != null) {
                    _blockUserList.value = bList.blockLists
                }
                Log.d("Cleo", "block success")
            }
            .addOnFailureListener {
                Log.d("Cleo", "block fail")
            }
    }

    private fun addComment() {

        val senderId = auth.currentUser?.email

        val data = hashMapOf(
            "content" to commentInput,
            "senderId" to senderId,
            "createdTime" to Timestamp.now(),
            "userName" to auth.currentUser?.displayName!!,
        )

        db.collection(POSTS_PATH)
            .document(posts.id)
            .collection(MESSAGES_SUB_POSTS)
            .add(data)
            .addOnSuccessListener {
                Log.d("JOMALONE ", "Add comment success!!")
            }
            .addOnFailureListener {
                Log.d("Cleo", "add fail")
            }
    }

    fun setPosts(newPosts: Posts) {
        posts = newPosts
    }

    fun setComment(comment: String){
        commentInput = comment
        addComment()
    }
}
