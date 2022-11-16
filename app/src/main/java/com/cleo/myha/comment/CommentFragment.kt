package com.cleo.myha.comment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cleo.myha.R
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.FragmentCommentBinding


class CommentFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCommentBinding.inflate(inflater, container, false)

        val posts = arguments?.getParcelable<Posts>("postsKey")
        Log.d("post", "$posts")




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

        return binding.root
    }

}