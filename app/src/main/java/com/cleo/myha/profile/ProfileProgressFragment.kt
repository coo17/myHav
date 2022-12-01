package com.cleo.myha.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cleo.myha.databinding.FragmentProfileProgressBinding
import kotlin.math.roundToInt


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

        viewModel.completedTasks.observe(viewLifecycleOwner, Observer {
            binding.unfinishedTask.text = "${it.toInt()} finished"
        })

        viewModel.percentTask.observe(viewLifecycleOwner, Observer {
            binding.textPercent.text = "${it.toInt()}%"
        })


        return binding.root
    }


//    private fun updatedProgressBar(){
//        ProgressBar
//    }

}

