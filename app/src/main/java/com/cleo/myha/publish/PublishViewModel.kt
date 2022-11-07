package com.cleo.myha.publish

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import java.time.Instant.now

class PublishViewModel: ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _postData = MutableLiveData<List<Posts>>()
    val postData: LiveData<List<Posts>>
        get() = _postData


    init {
//        addPost("","")

    }

//    fun addPost(title: String, content: String){
//        val user: MutableMap<String, Any> = HashMap()
//        user["title"] = title
//        user["content"] = content
//        user["author"] = "IU@gmail.com"
////        user["tag"] = tag
//        user["lastUpatedTime"] = com.google.firebase.Timestamp.now()
//
//        //Get Data
//        Log.d("Cleooo", "publishBtn")
//
//        db.collection("posts")
//            .add()
//            .addOnSuccessListener { documentReference ->
//                Log.d("Cleooo", "add success")
//                Log.d("Cleooo", "DocumentSnapshot added with ID: ${documentReference.id}")
//
//            }
//            .addOnFailureListener { e ->
//                Log.d("Cleooo", "add fail")
//                Log.w("Cleooo", "Error adding document", e)
//            }
//    }

}