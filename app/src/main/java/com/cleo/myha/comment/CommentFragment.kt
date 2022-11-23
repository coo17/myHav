package com.cleo.myha.comment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cleo.myha.R
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.FragmentCommentBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class CommentFragment : Fragment() {
    private var firebase = FirebaseFirestore.getInstance()
    private lateinit var posts: Posts
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCommentBinding.inflate(inflater, container, false)
        auth= FirebaseAuth.getInstance()
        val viewModel = ViewModelProvider(this)[CommentViewModel::class.java]
        posts = arguments?.getParcelable<Posts>("postsKey")!!
        Log.d("post", "$posts")
        viewModel.setPosts(posts)



        val adapter = CommentAdapter(viewModel)
        binding.commentRecycler.adapter = adapter






        viewModel.userComments.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })


        if (posts != null) {
            Glide.with(requireContext())
                .load(posts.photo)
//                .error(R.drawable.ic_iu)
                .into(binding.postImage)
        }
        binding.textTitle.text = posts!!.title
        binding.textContent.text = posts!!.content




        binding.backBtn.setOnClickListener {
            this.findNavController().navigateUp()
        }



        binding.sendBtn.setOnClickListener {
            addComment(
                binding.editTextComment.text.toString(),
            )

        }


        return binding.root
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy-MM-d hh:mm a", Locale.getDefault())
        return format.format(date)
    }

    private fun addComment(content: String) {

        val senderId = auth.currentUser?.let {
            it.email
        }

        val currentTime = Date().time
        val createdTime = convertLongToTime(currentTime)

        val data = hashMapOf(
            "content" to content,
            "senderId" to senderId,
            "createdTime" to Timestamp.now(),
            "userName" to auth.currentUser?.let {
                it.displayName
            }!!,
        )

        Log.d("OMG", "XXXXX ${data}")

        firebase.collection("posts")
            .document(posts.id)
            .collection("messages")
            .add(data)
            .addOnSuccessListener {
                Log.d("CLEOOO", "Success!!")
            }
            .addOnFailureListener { e ->
                Log.d("CLEOOO", "add fail")
            }
    }
//
//    private fun hideKeyboard() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }

}