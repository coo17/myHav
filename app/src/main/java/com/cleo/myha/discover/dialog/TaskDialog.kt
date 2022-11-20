package com.cleo.myha.discover.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import com.cleo.myha.comment.CommentAdapter
import com.cleo.myha.comment.CommentViewModel
import com.cleo.myha.data.CommentsInfo
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.DialogTaskBinding
import com.cleo.myha.habits.group.GroupViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class TaskDialog : AppCompatDialogFragment() {

    private var db = FirebaseFirestore.getInstance()

    private lateinit var binding: DialogTaskBinding
    private lateinit var habits: Habits

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTaskBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        habits = arguments?.getParcelable<Habits>("habitsKey")!!

        binding.textTask.text = habits!!.task
        binding.textCategory.text = habits!!.category
        binding.textDuration.text = habits!!.duration
//        binding.textRepeatedDays.text = habits.repeatedDays.toString()


        binding.joinBtn.setOnClickListener {
            addCard()
        }

        return binding.root
    }

    fun addCard() {

        val db = FirebaseFirestore.getInstance().collection("habits")
        val habitId = habits.id
        val mId = "Cleo@gmail.com"

        db.document(habitId)
            .update("members", FieldValue.arrayUnion(mId))
            .addOnSuccessListener {
                Log.d("Cleooo", "get success ,${it}")
                Log.d("TAG", "add success")
            }
            .addOnFailureListener {
                Log.d("2022", "add fail")
                Log.w("2022", "Error adding document", it)
            }

    }

}