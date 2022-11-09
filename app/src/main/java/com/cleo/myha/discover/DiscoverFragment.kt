package com.cleo.myha.discover



import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
//import com.applandeo.materialcalendarview.EventDay
import com.cleo.myha.R
import com.cleo.myha.databinding.FragmentDiscoverBinding
import java.util.*


class DiscoverFragment : Fragment() {

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiscoverBinding.inflate(inflater, container, false)

//
//        val events: MutableList<EventDay> = ArrayList()
//
//        val calendar = Calendar.getInstance()
////        val d1 = Calendar.getInstance()
////        d1.set(2022,11,9)
//
//        events.add(EventDay(calendar, R.drawable.ic_test_dot))
//
////        events.add(EventDay(calendar, R.drawable.sampe_icon, Color.parseColor("#228B22")))
//
//
//        binding.calendarView.setEvents(events)


        return binding.root
    }

}