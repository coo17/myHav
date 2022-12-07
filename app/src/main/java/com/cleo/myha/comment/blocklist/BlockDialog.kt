package com.cleo.myha.comment.blocklist

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.data.User
import com.cleo.myha.databinding.DialogBlockBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class BlockDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogBlockBinding
    private var db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogBlockBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[BlockViewModel::class.java]

        val email = BlockDialogArgs.fromBundle(requireArguments()).email
        binding.searchBar.setText(email)

        val adapter = BlockAdapter()

        binding.blockBtn.setOnClickListener {
            Log.d("Cleo", "Hi")
            addNewBlockUser()
        }

        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun addNewBlockUser() {

        val senderId = FirebaseAuth.getInstance()
            .currentUser?.let {
                it.email
            }.toString()

        val searchEmail = binding.searchBar.text.toString()

        Log.d("aesop", "$searchEmail")

        db.collection("users")
            .document(senderId)
            .update("blockLists", FieldValue.arrayUnion(searchEmail))
            .addOnSuccessListener {
                Log.d("Cleo", "You blocked it!")
            }.addOnFailureListener {
                Log.d("Cleo", "fail")
            }
    }
}
