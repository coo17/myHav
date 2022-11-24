package com.cleo.myha.home



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.R
import com.cleo.myha.databinding.FragmentHomeBinding
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


//        val calendars: List<Calendar> = ArrayList()

        val d1 = GregorianCalendar(2022,11,9)
        val d2 = GregorianCalendar(2022,11,22)
//        val events: MutableList<EventDay> = ArrayList()
//
//        val calendarss = Calendar.getInstance()
//        events.add(EventDay(calendarss, R.drawable.lion))
////
//
//        val calendar = Calendar.getInstance()
//        calendar[2022, 11] = 23
        binding.calendarView.selectedDates = listOf(d1,d2)

//        calendarView.setDate(calendar)

        return binding.root
    }

    

}