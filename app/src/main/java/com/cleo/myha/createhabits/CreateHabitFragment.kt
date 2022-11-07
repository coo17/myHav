package com.cleo.myha.createhabits


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.R
import com.cleo.myha.databinding.FragmentCreateHabitBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class CreateHabitFragment : Fragment() {

    private val firebase = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[CreateHabitViewModel::class.java]

//        val hours = CreateHabitFragmentArgs.fromBundle(requireArguments()).reminder
//        val minutes = CreateHabitFragmentArgs.fromBundle(requireArguments()).minutes
//
//
//
//        val reminderToFirebase = (hours.toLong())*60*60*1000 + (minutes.toLong()) *60*1000
//
//
//
//        binding.textSelectedReminder.text = "$newHour:$newMinutes"
//        binding.textSelectedReminder.setOnClickListener {
//            findNavController().navigate(CreateHabitFragmentDirections.actionGlobalTimePickerDialog())
//        }


        val spinner: Spinner = binding.spinnerCateogry
        val spinnerList = listOf("health", "workout", "reading", "learning", "general")
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            spinnerList
        )
        spinner.adapter = arrayAdapter

        var text = ""

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                text = spinnerList.get(p2)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val spinnerTimer: Spinner = binding.spinnerTimer

        val timerSetting = listOf(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60)
        val timerAdapter = ArrayAdapter(
            requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            timerSetting
        )
        spinnerTimer.adapter = timerAdapter

        var timer = ""

        spinnerTimer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                timer = timerSetting.get(p2).toLong().toString()


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        val timePicker = MaterialTimePicker.Builder().build()
        binding.textSelectedReminder.setOnClickListener {

            timePicker.show(
                parentFragmentManager, "time_picker"
            )
        }
        var reminderToFirebase = 0L
        timePicker.addOnPositiveButtonClickListener {
            val newHour = if (timePicker.hour == -1) {
                "00"
            } else timePicker.hour

            val newMinutes = if (timePicker.minute == -1 || timePicker.minute == 0) {
                "00"
            } else timePicker.minute
            binding.textSelectedReminder.text = "${newHour} : ${newMinutes}"
            reminderToFirebase =
                (timePicker.hour.toLong()) * 60 * 60 * 1000 + (timePicker.minute.toLong()) * 60 * 1000
        }


//        fun convertLongToTime(time: Long): String{
//            val date = Date(time)
//            val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
//            return format.format(date)
//        }

//        fun showDateRangePicker(){
//            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText("Select Date").build()
//
//            dateRangePicker.show(
//                childFragmentManager,"date_range_picker"
//            )
//
//            dateRangePicker.addOnPositiveButtonClickListener { dateSelected ->
//
//                val startDate = dateSelected.first
//                val endDate = dateSelected.second
//
//                if(startDate != null && endDate != null) {
//                    binding.textSelectedDuration.text =
//                        "${convertLongToTime(startDate)} " + "~ ${convertLongToTime(endDate)}"
//                    Log.d("startDate", "$startDate")
//                }
//            }
//
//        }
//
//        binding.textSelectedDuration.setOnClickListener {
//            showDateRangePicker()
//        }


        val monday = binding.textMon
        val tuesday = binding.textTue
        val wednesday = binding.textWed
        val thursday = binding.textThurs
        val friday = binding.textFri
        val saturday = binding.textSat
        val sunday = binding.textSun

        val list = mutableListOf<Boolean>(false, false, false, false, false, false, false)

       viewModel.weeklyData.observe( viewLifecycleOwner, androidx.lifecycle.Observer {
           if (it.get(0)){
                binding.textMon.setBackgroundResource(R.drawable.rounded_seleceted_days)
            }else{
                binding.textMon.setBackgroundResource(R.drawable.rounded_days)
            }
           if (it.get(1)){
               binding.textTue.setBackgroundResource(R.drawable.rounded_seleceted_days)
           }else{
               binding.textTue.setBackgroundResource(R.drawable.rounded_days)
           }
           if (it.get(2)){
               binding.textWed.setBackgroundResource(R.drawable.rounded_seleceted_days)
           }else{
               binding.textWed.setBackgroundResource(R.drawable.rounded_days)
           }
           if (it.get(3)){
               binding.textThurs.setBackgroundResource(R.drawable.rounded_seleceted_days)
           }else{
               binding.textThurs.setBackgroundResource(R.drawable.rounded_days)
           }
           if (it.get(4)){
               binding.textFri.setBackgroundResource(R.drawable.rounded_seleceted_days)
           }else{
               binding.textFri.setBackgroundResource(R.drawable.rounded_days)
           }
           if (it.get(5)){
               binding.textSat.setBackgroundResource(R.drawable.rounded_seleceted_days)
           }else{
               binding.textSat.setBackgroundResource(R.drawable.rounded_days)
           }
           if (it.get(6)){
               binding.textSun.setBackgroundResource(R.drawable.rounded_seleceted_days)
           }else{
               binding.textSun.setBackgroundResource(R.drawable.rounded_days)
           }
        }
       )

        monday.setOnClickListener {
            viewModel.selectDays(0)
        }
        tuesday.setOnClickListener {
            viewModel.selectDays(1)
        }
        wednesday.setOnClickListener {
            viewModel.selectDays(2)
        }
        thursday.setOnClickListener {
            viewModel.selectDays(3)
        }
        friday.setOnClickListener {
            viewModel.selectDays(4)
        }
        saturday.setOnClickListener {
            viewModel.selectDays(5)
        }
        sunday.setOnClickListener {
            viewModel.selectDays(6)
        }
//        Log.d("CCC", "${monday.text}")



        binding.btnSave.setOnClickListener {

            addData(
                text,
                binding.textSelectedDuration.text.toString(),
                binding.editTask.text.toString(),
                timer,
                reminderToFirebase,
                list
            )
            Log.d("ABC", "${timer}")
            findNavController().navigate(NavGraphDirections.actionGlobalHabitFragment())
        }

        binding.btnCancel.setOnClickListener{
            findNavController().navigate(NavGraphDirections.actionGlobalHabitFragment())
        }

        return binding.root
    }

    private fun addData(category:String, duration:String,  task: String, timer: String, reminder: Long, repeatedDays: List<Boolean>) {
        val articles = FirebaseFirestore.getInstance().collection("habits")
        val document = articles.document()
        val data = hashMapOf(
            "category" to category,
            "duration" to duration,
            "id" to document.id,
            "members" to
                    listOf<String>("IU", "Wayne"),
            "ownerId" to "IU@gmail.com",
            "reminder" to reminder,
            "task" to task,
            "timer" to timer,
            "repeatedDays" to repeatedDays
//            "repeatedDays" to listOf<Boolean>(true, true, true, true, false, false, false)

        )

        Log.d("OMG", "$data")

        firebase.collection("habits").add(data).addOnSuccessListener {
            Log.d("Cleooo", "Success!!")
        }
            .addOnFailureListener { e ->
                Log.d("Cleooo", "add fail")
            }
    }
}

