package com.cleo.myha.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentHomeBinding
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val adapter = HomeAdapter(
            HomeAdapter.HomeListener { checkbox: Boolean ->

                if (checkbox) {
                    if (viewModel.checkAllList()) {
                        findNavController().navigate(NavGraphDirections.actionGlobalFinishTaskDialog())
                    }
                }
            },
            viewModel
        )

        binding.taskRecyclerView.adapter = adapter
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.textHelloUser.text = "Hello, ${auth.currentUser?.displayName}"

        viewModel.doneList.observe(
            viewLifecycleOwner,
            Observer {
                adapter.notifyDataSetChanged()
            }
        )

        viewModel.todayTasks.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )

        viewModel.monthOfList.observe(
            viewLifecycleOwner,
            Observer {
                Log.d("Cleo", "Hey${it.size}")
                val list = mutableListOf<Event>()

                for (item in it) {
                    Log.d("Cleo", "Hi${item.value}")
                    if (item.value) {
                        val ev1 = Event(
                            Color.parseColor("#ABD3D6"),
                            item.key,
                            ""
                        )
                        list.add(ev1)
                    }
                }

                binding.compactcalendarView.addEvents(list)
            }
        )

        binding.compactcalendarView.setEventIndicatorStyle(1)

        // display certain daily tasks
        binding.compactcalendarView.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = binding.compactcalendarView.getEvents(dateClicked)
                viewModel.setDate(dateClicked)
                Log.d("TAG", "Day was clicked: $dateClicked with events $events")
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d("TAG", "Month was scrolled to: $firstDayOfNewMonth")
            }
        })

        return binding.root
    }
}
