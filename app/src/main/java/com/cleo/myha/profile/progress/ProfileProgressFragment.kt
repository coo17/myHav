package com.cleo.myha.profile.progress

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cleo.myha.databinding.FragmentProfileProgressBinding
import com.cleo.myha.profile.ProfileViewModel

class ProfileProgressFragment : Fragment() {

    private val animationDuration = 1000L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileProgressBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        val adapter = ProfileProgressAdapter()
        binding.trackerRecyclerView.adapter = adapter

        viewModel.categoryList.observe(
            viewLifecycleOwner
        ) {
            adapter.submitList(it)
        }

        viewModel.allHabits.observe(
            viewLifecycleOwner
        ) {
            binding.totalHabit.text = "${it.size} habits in total"
        }

        viewModel.allTasks.observe(
            viewLifecycleOwner
        ) {
            binding.finishedHabit.text = "${it.toInt()} total tasks"
        }

        viewModel.completedTasks.observe(
            viewLifecycleOwner
        ) {
            binding.unfinishedTask.text = "${it.toInt()} finished"
        }

        viewModel.percentTask.observe(
            viewLifecycleOwner
        ) {
            binding.textPercent.text = "${it.toInt()}%"
        }

        viewModel.donutSet.observe(
            viewLifecycleOwner
        ) {
            binding.donutChart.animate(it)
        }

        // display donut chart
        binding.donutChart.donutColors = intArrayOf(
            Color.parseColor("#102F55"),
            Color.parseColor("#CCD2CC"),
        )
        binding.donutChart.animation.duration = animationDuration

        val donutSet = listOf(
            0f,
            100f
        )

        binding.donutChart.animate(donutSet)

        return binding.root
    }
}
