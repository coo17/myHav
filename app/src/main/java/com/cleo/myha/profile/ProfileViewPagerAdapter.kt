package com.cleo.myha.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalArgumentException

class ProfileViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle):
FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { ProfilePostFragment() }
            1 -> { ProfileProgressFragment() }
            else -> {throw IllegalArgumentException("Error Type")
            }
        }
    }
}