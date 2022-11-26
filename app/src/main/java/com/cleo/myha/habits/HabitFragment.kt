package com.cleo.myha.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentHabitBinding
import com.cleo.myha.databinding.FragmentProfileBinding
import com.cleo.myha.habits.single.SingleAdapter
import com.cleo.myha.profile.ProfileViewPagerAdapter


class HabitFragment : Fragment() {

    private lateinit var binding: FragmentHabitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHabitBinding.inflate(inflater, container, false)
        val adapter = HabitViewPagerAdapter(childFragmentManager, lifecycle)

        binding.viewPager.adapter = HabitViewPagerAdapter(childFragmentManager, lifecycle)





        binding.text1.setOnClickListener {
            binding.viewPager.post {
                binding.viewPager.setCurrentItem(0,true)

            }
        }

        binding.text2.setOnClickListener {
            binding.viewPager.post {
                binding.viewPager.setCurrentItem(1,true)

            }
        }

        binding.buttonToCreate.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalCreateHabitFragment())
        }



        return binding.root
    }


}