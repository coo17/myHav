package com.cleo.myha.discover.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cleo.myha.comment.CommentAdapter
import com.cleo.myha.comment.CommentViewModel
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.DialogTaskBinding
import com.cleo.myha.habits.group.GroupViewModel

class TaskDialog : AppCompatDialogFragment() {

    private lateinit var binding : DialogTaskBinding
    private lateinit var habits: Habits

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTaskBinding.inflate(inflater, container, false)

        habits = arguments?.getParcelable<Habits>("habitsKey")!!

        binding.textTask.text = habits!!.task
        binding.textCategory.text = habits!!.category
        binding.textDuration.text = habits!!.duration
//        binding.textRepeatedDays.text = habits.repeatedDays.toString()


        binding.joinBtn.setOnClickListener {

        }

        return binding.root
    }



}