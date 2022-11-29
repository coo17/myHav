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
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.FragmentHomeBinding
import com.cleo.myha.databinding.FragmentPublishBinding
import com.cleo.myha.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class PublishFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPublishBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val viewModel = ViewModelProvider(this)[PublishViewModel::class.java]


//        viewModel.postData.observe(viewLifecycleOwner, Observer {
//
//        })


        binding.btnPublish.setOnClickListener {

            val tag = when (binding.chipGroup.checkedChipId) {
                R.id.chip1 -> "health"
                R.id.chip2 -> "workout"
                R.id.chip3 -> "reading"
                R.id.chip4 -> "learning"
                R.id.chip5 -> "general"
                else -> {
                    ""
                }
            }
            addData(
                binding.textPostTitle.text.toString(),
                binding.textPostContent.text.toString(),
                tag
            )
            findNavController().navigateUp()

        }

        return binding.root
    }

    fun convertLongToTime(time: Long): String{
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    private fun addData(title:String, content:String, tag: String) {
        val articles = FirebaseFirestore.getInstance().collection("posts")
        val postId = articles.document().id
        val authorEmail = auth.currentUser?.let {
            it.email
        }
        val createdTime = Date().time

        val lastUpdatedTime = convertLongToTime(createdTime)
        val data = hashMapOf(
            "title" to title,
            "content" to content,
            "id" to postId,
            "author" to authorEmail,
            "tag" to tag,
            "lastUpdatedTime" to lastUpdatedTime
        )


        db.collection("posts")
            .document(postId)
            .set(data)
            .addOnSuccessListener {
            Log.d("Cleooo", "Success!!")
        }
            .addOnFailureListener { e ->
                Log.d("Cleooo", "add fail")
            }
    }
}

