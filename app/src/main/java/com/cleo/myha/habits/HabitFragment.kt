package com.cleo.myha.habits

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.FragmentHabitBinding

class HabitFragment : Fragment() {

    private lateinit var binding: FragmentHabitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHabitBinding.inflate(inflater, container, false)
//        val adapter = HabitViewPagerAdapter(childFragmentManager, lifecycle)

        binding.viewPager.adapter = HabitViewPagerAdapter(childFragmentManager, lifecycle)

        val positionButton1 = binding.layoutSingle
        val positionButton2 = binding.layoutGroup

        binding.layoutSingle.setOnClickListener {
            ObjectAnimator.ofFloat(positionButton1, TRANSITION_PROPERTY_NAME, POSITION_MOVE_TO_LEFT).apply {
                duration = ANIMATOR_DURATION
                start()
            }
            ObjectAnimator.ofFloat(positionButton2, TRANSITION_PROPERTY_NAME, POSITION_MOVE_TO_RIGHT).apply {
                duration = ANIMATOR_DURATION
                start()
            }
            binding.viewPager.post {
                binding.viewPager.setCurrentItem(0, true)
            }
        }

        binding.layoutGroup.setOnClickListener {
            ObjectAnimator.ofFloat(positionButton1, TRANSITION_PROPERTY_NAME, POSITION_MOVE_TO_RIGHT).apply {
                duration = ANIMATOR_DURATION
                start()
            }
            ObjectAnimator.ofFloat(positionButton2, TRANSITION_PROPERTY_NAME, POSITION_MOVE_TO_LEFT).apply {
                duration = ANIMATOR_DURATION
                start()
            }

            binding.viewPager.post {
                binding.viewPager.setCurrentItem(1, true)
            }
        }

        binding.buttonToCreate.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalCreateHabitFragment())
        }

        return binding.root
    }


    companion object{

        private const val TRANSITION_PROPERTY_NAME = "TranslationX"
        private const val POSITION_MOVE_TO_RIGHT = 20f
        private const val POSITION_MOVE_TO_LEFT = -20f
        private const val ANIMATOR_DURATION = 200L

    }
}
