package com.cleo.myha.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.cleo.myha.R
import com.cleo.myha.databinding.FragmentProfileBinding
import com.cleo.myha.util.PROFILE_POSTS
import com.cleo.myha.util.PROFILE_PROGRESS
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val tabTitles = arrayListOf(PROFILE_POSTS, PROFILE_PROGRESS)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = ProfileViewPagerAdapter(childFragmentManager, lifecycle)

        setUpTabLayoutWithViewPager()

        binding.userAvatar.setImageResource(R.drawable.woman)

        return binding.root
    }

    private fun setUpTabLayoutWithViewPager() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}
