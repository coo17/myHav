package com.cleo.myha.chatroom

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
import com.cleo.myha.databinding.DialogBlockBinding
import com.cleo.myha.databinding.DialogFinishTaskBinding

class BlockDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogBlockBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogBlockBinding.inflate(inflater, container, false)


        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}

