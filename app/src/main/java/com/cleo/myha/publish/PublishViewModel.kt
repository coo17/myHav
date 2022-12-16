package com.cleo.myha.publish

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.CommentsInfo
import com.cleo.myha.data.Posts
import com.cleo.myha.util.POSTS_PATH
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class PublishViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()

    private var _postData = MutableLiveData<List<Posts>>()
    val postData: LiveData<List<Posts>>
        get() = _postData

    private var _photo = MutableLiveData<Uri>()
    val photo: LiveData<Uri>
        get() = _photo

    fun displayPhoto(image: Uri) {

        _photo.value = image
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat(CONVERT_TO_TIME, Locale.getDefault())
        return format.format(date)
    }

    fun setPost(title: String, content: String, tag: String, Uri: String) {
        val articles = FirebaseFirestore.getInstance().collection(POSTS_PATH)
        val postId = articles.document().id
        val authorEmail = auth.currentUser?.email

        val createdTime = Date().time

        val lastUpdatedTime = convertLongToTime(createdTime)


        val dataOfPost = authorEmail?.let {
            Posts(
                postId,
                it,
                title,
                content,
                lastUpdatedTime,
                Uri,
                tag,
                listOf<CommentsInfo>()
            )
        }


        if (dataOfPost != null) {
            db.collection(POSTS_PATH)
                .document(postId)
                .set(dataOfPost)
                .addOnSuccessListener {
                    Log.d("Cleo", " POST Success!!")
                }
                .addOnFailureListener { e ->
                    Log.d("Cleo", "add fail")
                }
        }
    }

    companion object {
        const val CONVERT_TO_TIME = "yyyy-MM-dd hh:mm a"

    }
}
