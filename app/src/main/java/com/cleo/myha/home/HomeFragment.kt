package com.cleo.myha.home


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentHomeBinding
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.github.sundeepk.compactcalendarview.domain.Event
import com.google.firebase.auth.FirebaseAuth
import convertToTime
import java.util.*
import kotlin.time.Duration.Companion.seconds


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
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        binding.textHelloUser.text = "Hello, ${auth.currentUser?.displayName}" ?: "User"

        viewModel.doneList.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.todayTasks.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.monthOfList.observe(viewLifecycleOwner, Observer {
            Log.d("BBBBB", "${it.size}")
            val list = mutableListOf<Event>()

            for(item in it){
                Log.d("BBBBB", "${item.value}")
                if(item.value == true){
                val ev1 = Event(
                    Color.YELLOW,
                    item.key.timeInMillis,
                    "i don't know why"
                )
                list.add(ev1)

            }}

            binding.compactcalendarView.addEvents(list)
        })



        val compactCalendar = Calendar.getInstance()
        compactCalendar.set(2022, 11, 5)


        binding.compactcalendarView.setEventIndicatorStyle(1)


        // display certain daily tasks
        binding.compactcalendarView.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = binding.compactcalendarView.getEvents(dateClicked)
                viewModel.setDate(dateClicked)
                Toast.makeText(
                    context,
                    "Day was clicked: $dateClicked with events $events",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("TAG", "Day was clicked: $dateClicked with events $events")
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d("TAG", "Month was scrolled to: $firstDayOfNewMonth")
            }
        })

        return binding.root
    }


}