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

//        val tabLayout = binding.tabLayout
//        val viewPager = binding.viewPager
        binding.viewPager.adapter = ProfileViewPagerAdapter(childFragmentManager, lifecycle)

//        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
//            tab.text = when(position){
//                0 ->  "Post"
//                1 ->  "Progress"
//                else -> { throw IllegalArgumentException("Not found")
//                }
//            }
//        }
        setUpTabLayoutWithViewPager()


//        binding.textBio.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//                if(binding.textBio.text.toString() != "")
//                    binding.textBio.setText("${binding.textBio.text.toString().toFloat()}")
//
//                else  binding.textBio.setText("0")
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//        })
//
//        binding.textBio.doAfterTextChanged {
//            binding.textBio.text = it
//        }

        binding.createPost.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalPublishFragment())
        }

        return binding.root
    }

    private fun setUpTabLayoutWithViewPager() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

//        for(i in 0..2){
//            val tabTextView = LayoutInflater.from(requireContext()).inflate(R.layout.item_tab, null) as TextView
//            binding.tabLayout.getTabAt(i)?.customView = tabTextView
//        }

    }

}