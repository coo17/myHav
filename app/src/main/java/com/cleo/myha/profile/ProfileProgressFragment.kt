package com.cleo.myha.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.cleo.myha.databinding.FragmentProfileProgressBinding


class ProfileProgressFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileProgressBinding.inflate(inflater, container, false)





        return binding.root
    }


//    private fun updatedProgressBar(){
//        ProgressBar
//    }

}

