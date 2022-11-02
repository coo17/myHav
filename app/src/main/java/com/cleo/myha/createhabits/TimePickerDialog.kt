package com.cleo.myha.createhabits

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.R
import com.cleo.myha.databinding.DialogTimerPickerBinding
import com.cleo.myha.databinding.FragmentHabitBinding


class TimePickerDialog : AppCompatDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.TimerPickerDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DialogTimerPickerBinding.inflate(inflater, container, false)

//        binding.timePicker.setIs24HourView(true)
        var taskHourDay = -1
        var taskMinute = -1


        binding.timePicker.setOnTimeChangedListener{ view, hourDay, minute ->
            var textView = binding.textView
            Log.d("cleooo", "$hourDay")
//            var hour = hourDay
//            var minute = minute
            taskHourDay = hourDay
            taskMinute = minute

//            var amPm = ""
//            when{
//                hour == 0 -> {
//                    hour += 12
//                    amPm = "AM"
//                }
//                hour == 1 -> amPm = "PM"
//                hour > 12 -> {
//                    hour -= 12
//                    amPm = "PM"
//                }
//                else ->
//                    amPm = "AM"
//            }
//            if(textView != null){
//                val hour = if(hour <10)"0" + hour else hour
//                val min = if(minute <10)"0" + minute else minute
//                val toast = "Time is: $hour : $min $amPm "
//                textView.text = toast
//                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
//            }
        }


        binding.saveTimeBtn.setOnClickListener{
            findNavController().navigate(NavGraphDirections.actionGlobalCreateHabitFragment(taskHourDay,taskMinute))
        }
        return binding.root
    }
}