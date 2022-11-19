package com.cleo.myha.habits

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cleo.myha.habits.group.GroupFragment
import com.cleo.myha.habits.single.SingleFragment
import java.lang.IllegalArgumentException

class HabitViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { SingleFragment() }
            1 -> { GroupFragment() }
            else -> {throw IllegalArgumentException("Error Type")
            }
        }
    }
}