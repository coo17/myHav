package com.cleo.myha.chatroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cleo.myha.databinding.FragmentChatRoomsBinding



class ChatRoomFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatRoomsBinding.inflate(inflater, container, false)



        binding.backBtn.setOnClickListener{
            findNavController().navigateUp()
        }

        return binding.root
    }
}

