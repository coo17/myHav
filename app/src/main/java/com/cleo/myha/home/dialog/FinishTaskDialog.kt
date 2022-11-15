package com.cleo.myha.home.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.databinding.DialogFinishTaskBinding

class FinishTaskDialog : AppCompatDialogFragment() {

    private lateinit var binding : DialogFinishTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFinishTaskBinding.inflate(inflater, container, false)

//        binding.lottieAnimationView.setAnimation("complete.json")
//        binding.lottieAnimationView.playAnimation()
//


        binding.postBtn.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalPublishFragment())
        }

        binding.closeBtn.setOnClickListener {
            findNavController().navigateUp()
        }


        return binding.root
    }

//    fun setupAnimation(){
//        val animation = findViewById<LottieAnimationView>(R.id.progressBar)
//        animation.speed = 2.0F // How fast does the animation play
//        animation.progress = 50F // Starts the animation from 50% of the beginning
//        animation.addAnimatorUpdateListener {
//// Called every time the frame of the animation changes
//        }
//        animation.repeatMode = LottieDrawable.RESTART // Restarts the animation (you can choose to reverse it as well)
//        animation.cancelAnimation() // Cancels the animation
//    }
}