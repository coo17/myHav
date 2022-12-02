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
import java.util.*


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


        val compactCalendar = Calendar.getInstance()
        compactCalendar.set(2022,11,5)
        val ev1 = Event(Color.GREEN, compactCalendar.timeInMillis, "Some extra data that I want to store.")
        val ev2 = Event(Color.BLACK, compactCalendar.timeInMillis, "Some extra data that I want to store.")

        binding.compactcalendarView.displayOtherMonthDays(true)
        binding.compactcalendarView.setEventIndicatorStyle(1)
//        binding.compactcalendarView.setEventIndicatorStyle(1)

        binding.compactcalendarView.addEvent(ev1)
        binding.compactcalendarView.addEvent(ev2)
        Log.d("TAG", "today $ev1")


        // define a listener to receive callbacks when certain events happen.
       binding.compactcalendarView.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val events: List<Event> = binding.compactcalendarView.getEvents(dateClicked)
                Toast.makeText(context,"Day was clicked: $dateClicked with events $events",Toast.LENGTH_SHORT).show()
                Log.d("TAG", "Day was clicked: $dateClicked with events $events")
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                Log.d("TAG", "Month was scrolled to: $firstDayOfNewMonth")
            }
        })

//        val d1 = GregorianCalendar(2022,11,9)
//        val d2 = GregorianCalendar(2022,11,10)
//        val d3 = GregorianCalendar(2022,11,11)
//        val d4 = GregorianCalendar(2022,11,16)
//        val d5 = GregorianCalendar(2022,11,17)
//        val d6 = GregorianCalendar(2022,11,22)
//        val d7 = GregorianCalendar(2022,11,28)
//
//        val events : MutableList<EventDay> = ArrayList()
//        val calendar = Calendar.getInstance()
//        calendar.set(2022,10,10)
//        events.add(EventDay(calendar, R.drawable.ic_add))
//

//        binding.calendarView.selectedDates = listOf(d1,d2,d3,d4,d5,d6,d7)
//        binding.calendarView.setEvents(events)
//


        return binding.root
    }

    

}