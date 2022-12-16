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
import com.cleo.myha.databinding.DialogBlockBinding


class BlockDialog : AppCompatDialogFragment() {

    private lateinit var binding: DialogBlockBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = DialogBlockBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[BlockViewModel::class.java]


        val email = BlockDialogArgs.fromBundle(requireArguments()).email
        binding.searchBar.setText(email)

        viewModel.navigateUp.observe(viewLifecycleOwner) {
            findNavController().navigateUp()
        }

        binding.blockBtn.setOnClickListener {
            Log.d("Cleo", "Hi")
            viewModel.setEmail(binding.searchBar.text.toString())
        }

        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}
