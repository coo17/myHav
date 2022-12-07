package com.cleo.myha.habits.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentGroupBinding

class GroupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGroupBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[GroupViewModel::class.java]

        val adapter = GroupAdapter(
            onClickListener = GroupAdapter.OnClickListener { Habits ->
                this.findNavController()
                    .navigate(NavGraphDirections.actionGlobalChatRoomsFragment(Habits))
            },
            viewModel
        )

        binding.groupRecycler.adapter = adapter

        viewModel.groupHabits.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )

        return binding.root
    }
}
