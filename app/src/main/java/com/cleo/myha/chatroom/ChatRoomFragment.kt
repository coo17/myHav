package com.cleo.myha.chatroom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.cleo.myha.chatroom.currentuser.CurrentUserAdapter
import com.cleo.myha.data.Habits
import com.cleo.myha.data.MessagesInfo
import com.cleo.myha.databinding.FragmentChatRoomsBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


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

        val userAdapter = CurrentUserAdapter()
        binding.userRecyclerView.adapter = userAdapter




        viewModel.sender.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            userAdapter.submitList(it)
        })

        binding.sendBtn.setOnClickListener {
            addComment(
                binding.textInput.text.toString()
            )
        }


        binding.dotBtn.setOnClickListener {
            findNavController().navigate(ChatRoomFragmentDirections.actionGlobalBlockDialog())
        }


        binding.backBtn.setOnClickListener{
            findNavController().navigateUp()
        }
        return binding.root
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

        Log.d("C", "comment ${senderId}")

        db.collection("habits")
            .document(habit.id)
            .collection("messages")
            .add(data)
            .addOnSuccessListener {
                Log.d("Cleo", "Get Comment Success!!")
            }
            .addOnFailureListener { e ->
                Log.d("Cleo", "add fail")
            }
    }

}

