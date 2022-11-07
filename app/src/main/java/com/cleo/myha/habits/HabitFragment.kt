package com.cleo.myha.habits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentHabitBinding


class HabitFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHabitBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[HabitViewModel::class.java]
        val adapter = HabitAdapter()
        binding.singleRecycler.adapter = adapter





        binding.buttonToCreate.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalCreateHabitFragment())
        }


        viewModel.singleTask.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

}