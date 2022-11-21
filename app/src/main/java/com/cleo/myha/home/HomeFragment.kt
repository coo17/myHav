package com.cleo.myha.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar


class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val adapter = HomeAdapter(HomeAdapter.HomeListener { checkbox: Boolean ->

            if (checkbox == true) {
                if (viewModel.checkAllList()) {
                    findNavController().navigate(NavGraphDirections.actionGlobalFinishTaskDialog())
                }
            }
        }, viewModel)

        binding.taskRecyclerView.adapter = adapter
//        auth = FirebaseAuth.getInstance()
//        val user = auth.currentUser
//
//        if (user != null) {
//            binding.textHelloUser.text = auth.currentUser?.displayName ?: ""
//        }else{
//            binding.textHelloUser.text = "User"
//        }

        viewModel.doneList.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.todayTasks.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

}