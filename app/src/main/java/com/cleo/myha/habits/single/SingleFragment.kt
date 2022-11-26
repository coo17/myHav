package com.cleo.myha.habits.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentSingleBinding
import com.cleo.myha.habits.group.GroupAdapter

class SingleFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSingleBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[SingleViewModel::class.java]

        val adapter = SingleAdapter()
//        val adapter = SingleAdapter(onClickListener = SingleAdapter.OnClickListener { Habits ->
//            this.findNavController()
//                .navigate(NavGraphDirections.actionGlobalChatRoomsFragment(Habits))
//        }, viewModel)

        binding.singleRecycler.adapter = adapter


        val simpleCallBack = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, 0){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                adapter.notifyItemMoved(fromPosition, toPosition)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }
        }




        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(binding.singleRecycler)


        viewModel.yourHabits.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.root.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalHabitDetailFragment())
        }

        return binding.root
    }
}