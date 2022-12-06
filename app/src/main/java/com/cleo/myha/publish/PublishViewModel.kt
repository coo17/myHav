package com.cleo.myha.publish

import android.net.Uri
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

    private var _postData = MutableLiveData<List<Posts>>()
    val postData: LiveData<List<Posts>>
        get() = _postData

    private var _photo = MutableLiveData<Uri>()
    val photo: LiveData<Uri>
    get() = _photo


    init {
//        addPost("","")

    }

    fun displayPhoto(image:Uri){

        _photo.value = image
    }

}