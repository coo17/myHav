package com.cleo.myha.createhabits


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentCreateHabitBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp


class CreateHabitFragment : Fragment() {

    private val firebase = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCreateHabitBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[CreateHabitViewModel::class.java]

        val hours = CreateHabitFragmentArgs.fromBundle(requireArguments()).reminder
        val minutes = CreateHabitFragmentArgs.fromBundle(requireArguments()).minutes


        val newHour = if (hours == -1){
            "00"
        }else hours

        val newMinutes = if (minutes == -1 || minutes ==0){
            "00"
        }else minutes

        val reminderToFirebase = (hours.toLong())*60*60*1000 + (minutes.toLong()) *60*1000

        

        binding.textSelectedReminder.text = "$newHour:$newMinutes"
        binding.textSelectedReminder.setOnClickListener {
            findNavController().navigate(CreateHabitFragmentDirections.actionGlobalTimePickerDialog())
        }


        val spinner: Spinner = binding.spinnerCateogry
        val spinnerList = listOf("health", "workout", "reading", "learning", "general")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, spinnerList)
        spinner.adapter = arrayAdapter

        var text = ""

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                text = spinnerList.get(p2)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spinnerTimer: Spinner = binding.spinnerTimer
        val timerSetting = listOf(0, 5, 10)

//        val timerSetting = listOf("0", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55", "60")
        val timerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, timerSetting)
        spinnerTimer.adapter = timerAdapter

        var timer = ""

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                timer = timerSetting.get(p2).toLong().toString()


            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        binding.btnSave.setOnClickListener {

            addData(
                text,
                binding.editTask.text.toString(),
                timer,
                reminderToFirebase
            )
            Log.d("ABC", "${timer}")
            findNavController().navigate(NavGraphDirections.actionGlobalHabitFragment())
        }

        binding.btnCancel.setOnClickListener{
            findNavController().navigate(NavGraphDirections.actionGlobalHabitFragment())
        }

        return binding.root
    }

    private fun addData(category:String, task: String, timer: String, reminder: Long) {
        val articles = FirebaseFirestore.getInstance().collection("habits")
        val document = articles.document()
        val data = hashMapOf(
            "category" to category,
            "duration" to "duration",
            "id" to document.id,
            "members" to
                    listOf<String>("IU", "Wayne"),
            "ownerId" to "IU@gmail.com",
            "reminder" to reminder,
            "task" to task,
            "timer" to timer,
            "repeatedDays" to listOf<Boolean>(true, true, true, true, false, false, false)

        )

        Log.d("OMG", "$data")
//        document.set(data)
        firebase.collection("habits").add(data).addOnSuccessListener {
            Log.d("Cleooo", "Success!!")
        }
            .addOnFailureListener { e ->
                Log.d("Cleooo", "add fail")
            }
    }
}

