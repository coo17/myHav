package com.cleo.myha.profile

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.R
import com.cleo.myha.databinding.FragmentProfileBinding
import com.cleo.myha.home.HomeAdapter
import com.cleo.myha.home.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val tabTitles = arrayListOf("Post", "Progress")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = ProfileViewPagerAdapter(childFragmentManager, lifecycle)

        setUpTabLayoutWithViewPager()


        binding.userAvatar.setImageResource(R.drawable.woman)




//        binding.createPost.setOnClickListener {
//            findNavController().navigate(NavGraphDirections.actionGlobalPublishFragment())
//        }

        return binding.root
    }

    private fun setUpTabLayoutWithViewPager() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}