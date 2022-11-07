package com.cleo.myha.publish

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.R
import com.cleo.myha.databinding.FragmentHomeBinding
import com.cleo.myha.databinding.FragmentPublishBinding
import com.cleo.myha.home.HomeViewModel
import com.google.firebase.firestore.FirebaseFirestore


class PublishFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPublishBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[PublishViewModel::class.java]


//        viewModel.postData.observe(viewLifecycleOwner, Observer {
//
//        })


        binding.btnPublish.setOnClickListener {

            addData(
                binding.textPostTitle.text.toString(),
                binding.textPostContent.text.toString(),

            )

        }

        return binding.root
    }

    private fun addData(title:String, content:String) {
        val articles = FirebaseFirestore.getInstance().collection("habits")
        val document = articles.document()
        val data = hashMapOf(
            "title" to title,
            "content" to content,
            "id" to document.id,
            "tag" to "tag"
//            "lastUpdatedTime" to reminder
        )

        Log.d("OMG", "$data")

        db.collection("posts").add(data).addOnSuccessListener {
            Log.d("Cleooo", "Success!!")
        }
            .addOnFailureListener { e ->
                Log.d("Cleooo", "add fail")
            }
    }

    fun addPost(title: String, content: String){
        val user: MutableMap<String, Any> = HashMap()
        user["title"] = title
        user["content"] = content
        user["author"] = "IU@gmail.com"
////        user["tag"] = tag
        user["lastUpatedTime"] = com.google.firebase.Timestamp.now()

        //Get Data
        Log.d("Cleooo", "publishBtn")

        db.collection("posts")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Cleooo", "add success")
                Log.d("Cleooo", "DocumentSnapshot added with ID: ${documentReference.id}")

            }
            .addOnFailureListener { e ->
                Log.d("Cleooo", "add fail")
                Log.w("Cleooo", "Error adding document", e)
            }
    }
}

