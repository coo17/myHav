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

    private fun addData(title:String, content:String, tag: String) {
        val articles = FirebaseFirestore.getInstance().collection("posts")
        val postId = articles.document().id
        val data = hashMapOf(
            "title" to title,
            "content" to content,
            "id" to postId,
            "tag" to tag
//            "lastUpdatedTime" to reminder
        )

        Log.d("OMG", "ddddd $data")

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

