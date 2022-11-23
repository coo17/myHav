package com.cleo.myha.chatroom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.comment.CommentAdapter
import com.cleo.myha.comment.CommentViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.data.MessagesInfo
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.FragmentChatRoomsBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class ChatRoomFragment : Fragment() {

    private var db = FirebaseFirestore.getInstance()
    private lateinit var habit: Habits
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatRoomsBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[ChatRoomViewModel::class.java]

        habit = arguments?.getParcelable<Habits>("habitsKey")!!
        viewModel.setHabits(habit)

        auth= FirebaseAuth.getInstance()

        val adapter = ChatRoomAdapter()
        binding.chatRecyclerView.adapter = adapter



        viewModel.sender.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


        binding.sendBtn.setOnClickListener {
            addComment(
                binding.textInput.text.toString()
            )
        }


        binding.backBtn.setOnClickListener{
            findNavController().navigateUp()
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

        val data = MessagesInfo(
            senderEmail = senderId.toString(),
            senderName = auth.currentUser?.let {
                it.displayName
            }!!,
            senderImage = auth.currentUser?.photoUrl.toString(),
            message = content,
            textTime =  Timestamp.now()

        )

        Log.d("OMG", "comment ${senderId}")

        db.collection("habits")
            .document(habit.id)
            .collection("messages")
            .add(data)
            .addOnSuccessListener {
                Log.d("CLEOOO", "Success!!")
            }
            .addOnFailureListener { e ->
                Log.d("CLEOOO", "add fail")
            }
    }

}

