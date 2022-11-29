package com.cleo.myha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ContentInfoCompat.Flags
import com.cleo.myha.databinding.ActivityLoginBinding
import com.cleo.myha.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottieAnimationView1.apply {
            animate().setDuration(6000).alpha(1f).withEndAction {
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
}
