package com.cleo.myha.discover

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cleo.myha.data.Category

class DiscoverViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 6

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> { DiscoverItemFragment.newInstance(Category.All) }
            1 -> { DiscoverItemFragment.newInstance(Category.Health) }
            2 -> { DiscoverItemFragment.newInstance(Category.Workout) }
            3 -> { DiscoverItemFragment.newInstance(Category.Learning) }
            4 -> { DiscoverItemFragment.newInstance(Category.Reading) }
            5 -> { DiscoverItemFragment.newInstance(Category.General) }
            else -> {
                throw IllegalArgumentException("Error Type")
            }
        }
    }
}
