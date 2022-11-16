package com.cleo.myha.discover


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.cleo.myha.R
import com.cleo.myha.component.CenterZoomLayoutManager
import com.cleo.myha.databinding.FragmentDiscoverBinding
import com.cleo.myha.discover.community.CommunityAdapter
import com.cleo.myha.discover.community.CommunityViewModel
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Date.from


class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private val tabTitles =
        arrayListOf("all", "health", "workout", "learning", "reading", "general")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = DiscoverViewPagerAdapter(childFragmentManager, lifecycle)

        val viewModel = ViewModelProvider(this)[CommunityViewModel::class.java]
        val adapter = CommunityAdapter()




        binding.groupTaskRecycler.adapter = adapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.groupTaskRecycler)

        binding.groupTaskRecycler.layoutManager =
            CenterZoomLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.groupTaskRecycler.scrollToPosition(500)

        viewModel.groupTask.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })








        setUpTabLayoutWithViewPager()
        return binding.root

    }

    private fun setUpTabLayoutWithViewPager() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}

