package com.cleo.myha

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cleo.myha.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottieAnimationView1.apply {
            animate().setDuration(ANIMATION_DURATION).alpha(ANIMATION_ALPHA).withEndAction {
//                val i = Intent(context, MainActivity::class.java)
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(i)
//                finish()
            }
        }

        binding.button.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }

    companion object{
        private const val ANIMATION_DURATION = 6000L
        private const val ANIMATION_ALPHA = 1f
    }

}
