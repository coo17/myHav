package com.cleo.myha.home.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.DialogFinishTaskBinding

class FinishTaskDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogFinishTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogFinishTaskBinding.inflate(inflater, container, false)


        binding.postBtn.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalPublishFragment())
        }

        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }


        return binding.root
    }

}