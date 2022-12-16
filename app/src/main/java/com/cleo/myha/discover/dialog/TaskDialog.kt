package com.cleo.myha.discover.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cleo.myha.chatroom.ChatRoomViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.DialogTaskBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class TaskDialog : AppCompatDialogFragment() {


    private lateinit var binding: DialogTaskBinding
    private lateinit var habits: Habits

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogTaskBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        habits = arguments?.getParcelable<Habits>("habitsKey")!!

        val viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        viewModel.setHabitCard(habits)

        binding.textTask.text = habits.task
        binding.textCategory.text = habits.category
        binding.textDuration.text = habits.duration



        binding.joinBtn.setOnClickListener {
            viewModel.joinCardToMine
        }

        return binding.root
    }
}
