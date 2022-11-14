package com.cleo.myha.discover.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.cleo.myha.databinding.DialogTaskBinding

class TaskDialog : AppCompatDialogFragment() {

    private lateinit var binding : DialogTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTaskBinding.inflate(inflater, container, false)



        return binding.root
    }
}