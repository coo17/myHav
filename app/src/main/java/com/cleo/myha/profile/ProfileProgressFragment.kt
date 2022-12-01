package com.cleo.myha.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cleo.myha.databinding.FragmentProfileProgressBinding


class ProfileProgressFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileProgressBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]


        viewModel.allHabits.observe(viewLifecycleOwner, Observer {
            binding.totalHabit.text = "${it.size} habits in total"
        })

        viewModel.allTasks.observe(viewLifecycleOwner, Observer {
            binding.finishedHabit.text = "${it.toInt()} total tasks"
        })



        return binding.root
    }


//    private fun updatedProgressBar(){
//        ProgressBar
//    }

}

