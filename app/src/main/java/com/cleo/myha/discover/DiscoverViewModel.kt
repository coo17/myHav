package com.cleo.myha.discover

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.cleo.myha.profile.ProfilePostAdapter
import com.google.firebase.firestore.FirebaseFirestore

class DiscoverViewModel : ViewModel() {

    private var db = FirebaseFirestore.getInstance()

    private var _allPost = MutableLiveData<List<Posts>>()
    val allPost: LiveData<List<Posts>>
    get() = _allPost



    init {
        addPost()
    }

    private fun addPost(){

         db.collection("posts")
            .get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Posts>()
                Log.d("Vicc22","${documents.size()}")

                for(document in documents) {
                    val user = documents.toObjects(Posts::class.java)
                }

            }
            .addOnFailureListener {
                Log.d("Cleooo", "get fail")}
    }
}