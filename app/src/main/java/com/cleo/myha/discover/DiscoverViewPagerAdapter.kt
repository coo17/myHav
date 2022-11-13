package com.cleo.myha.discover

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cleo.myha.home.HomeFragment
import com.cleo.myha.profile.ProfilePostFragment
import com.cleo.myha.profile.ProfileProgressFragment
import java.lang.IllegalArgumentException

class DiscoverViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount() = 6

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { DiscoverItemFragment() }
            1 -> { DiscoverItemFragment() }
            2 -> { DiscoverItemFragment() }
            3 -> { DiscoverItemFragment() }
            4 -> { DiscoverItemFragment() }
            5 -> { DiscoverItemFragment() }
            else -> {throw IllegalArgumentException("Error Type")
            }
        }
    }
}