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
                val list = documents.toObjects(Posts::class.java)
                Log.d("Vicc22","${documents.size()}")

//                for(document in documents) {
//                    val user = documents.toObjects(Posts::class.java)
//                    list.add(user)
//                }

                _allPost.value = list

//                _allPost.value = list.filter {
//                    it.tag == "health"
//                }

            }
            .addOnFailureListener {
                Log.d("Cleooo", "get fail")}
    }

    private fun getData(){


    }
}
