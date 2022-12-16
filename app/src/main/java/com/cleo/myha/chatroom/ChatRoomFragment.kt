package com.cleo.myha.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.chatroom.currentuser.CurrentUserAdapter
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.FragmentChatRoomsBinding
import com.google.firebase.auth.FirebaseAuth
import hideKeyboard

class ChatRoomFragment : Fragment() {

    private lateinit var habit: Habits
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentChatRoomsBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[ChatRoomViewModel::class.java]

        habit = ChatRoomFragmentArgs.fromBundle(requireArguments()).habitsKey
        viewModel.setHabits(habit)

        auth = FirebaseAuth.getInstance()

        val adapter = ChatRoomAdapter()
        binding.chatRecyclerView.adapter = adapter

        val userAdapter = CurrentUserAdapter()
        binding.userRecyclerView.adapter = userAdapter

        binding.textTitle.text = habit.task

        // 顯示聊天室內容
        viewModel.sender.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // 顯示所有加入此habit的會員
        viewModel.userInfo.observe(viewLifecycleOwner) {
            userAdapter.submitList(it)
        }

        binding.sendBtn.setOnClickListener {
            viewModel.sendTextInput(binding.textInput.text.toString())
            binding.textInput.setText("")
            hideKeyboard(binding.textInput)
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}
