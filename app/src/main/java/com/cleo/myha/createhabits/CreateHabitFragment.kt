package com.cleo.myha.createhabits


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentCreateHabitBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore


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



        binding.textSelectedReminder.text = "$hours:$minutes"
        binding.textSelectedTimer.text = "$minutes minutes"
        binding.textSelectedReminder.setOnClickListener{
            findNavController().navigate(CreateHabitFragmentDirections.actionGlobalTimePickerDialog())
        }


        binding.btnSave.setOnClickListener{

            findNavController().navigate(NavGraphDirections.actionGlobalHabitFragment())
        }
        addData()
        return binding.root
    }

    fun addData() {
        val articles = FirebaseFirestore.getInstance().collection("habits")
        val document = articles.document()
        val data = hashMapOf(
            "task" to "",
            "members" to "",
            "duration" to  Timestamp.now(),
            "id" to document.id,
            "category" to "health"
        )
        Log.d("OMG", "$data")
        document.set(data)
    firebase.collection("habits").add("userHabit").addOnSuccessListener {
        Log.d("Cleooo","Success!!")
    }
    .addOnFailureListener { e ->
        Log.d("Cleooo", "add fail")
    }
}
//    }
//    fun addHabit(){
//   val articles = FirebaseFirestore.getInstance().collection("habits")
//        val document = articles.document()
//        val userHabit = hashMap()
//        userHabit["category"] = ""
//        userHabit["duration"] = "duration"
//        userHabit["id"] = document.id
//        userHabit["members"] = ""
//                userHabit["ownerId"] = ""
//                userHabit["reminder"] = ""
//                userHabit["repeatedDays"] = ""
//        userHabit["task"] = task
//
//        Log.d("cleooo", "$userHabit")
//
//        firebase.collection("habits").add("userHabit").addOnSuccessListener {
//            Log.d("Cleooo","Success!!")
//        }
//            .addOnFailureListener { e ->
//                Log.d("Cleooo", "add fail")
//            }
//    }

}

