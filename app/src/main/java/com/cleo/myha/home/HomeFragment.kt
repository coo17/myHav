package com.cleo.myha.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cleo.myha.R
import com.cleo.myha.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[HomeTaskViewModel::class.java]
        val adapter = HomeAdapter()
        binding.taskRecyclerView.adapter = adapter



        viewModel.todayTasks.observe(viewLifecycleOwner, Observer {

            adapter.submitList(it)
        })
        return binding.root
    }
}