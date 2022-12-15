package com.cleo.myha.comment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cleo.myha.R
import com.cleo.myha.chatroom.ChatRoomFragmentArgs
import com.cleo.myha.chatroom.ChatRoomFragmentDirections
import com.cleo.myha.data.Posts
import com.cleo.myha.data.User
import com.cleo.myha.databinding.FragmentCommentBinding
import com.cleo.myha.util.MESSAGES_SUB_POSTS
import com.cleo.myha.util.POSTS_PATH
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import hideKeyboard
import java.text.SimpleDateFormat
import java.util.*

class CommentFragment : Fragment() {

    private lateinit var posts: Posts
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCommentBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[CommentViewModel::class.java]

        auth = FirebaseAuth.getInstance()
        posts = CommentFragmentArgs.fromBundle(requireArguments()).postsKey

        viewModel.setPosts(posts)
        val storageReference = FirebaseStorage.getInstance()
        val adapter = CommentAdapter(
            CommentAdapter.OnClickListener {
                findNavController().navigate(ChatRoomFragmentDirections.actionGlobalBlockDialog(it))
            }
        )
        binding.commentRecycler.adapter = adapter


        viewModel.blockUserList.observe(viewLifecycleOwner) {
            viewModel.getComments(it)
        }

        viewModel.userComments.observe(viewLifecycleOwner) {
            adapter.submitList(it)
//            binding.commentRecycler.scrollToPosition(adapter.currentList.size - 0)
        }

//        Glide.with(requireContext())
//            .load(posts.photo)
//            .into(binding.postImage)

        posts.photo?.let { it ->
            storageReference.getReferenceFromUrl(it).downloadUrl.addOnSuccessListener { imgPhoto ->
                Glide.with(requireContext())
                    .load(imgPhoto)
                    .error(R.drawable.man)
                    .into(binding.postImage)
            }
        }

        binding.textTitle.text = posts.title
        binding.textContent.text = posts.content

        binding.backBtn.setOnClickListener {
            this.findNavController().navigateUp()
        }

        binding.sendBtn.setOnClickListener {

            viewModel.setComment(binding.editTextComment.text.toString())
            binding.editTextComment.setText("")
            hideKeyboard(binding.editTextComment)

        }

        return binding.root
    }
}
