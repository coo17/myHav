package com.cleo.myha

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cleo.myha.databinding.ActivityMainBinding

const val REQUEST_CODE_SIGN_IN = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHomeFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        val navController = navHomeFragment!!.findNavController()

        navController.addOnDestinationChangedListener { controller, destination, argument ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.habitFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.discoverFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.profileFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.loginFragment -> {
                    binding.bottomNav.visibility = View.GONE
                }
            }
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_home -> {
                    findNavController(R.id.my_nav_host_fragment).navigate(NavGraphDirections.actionGlobalHomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.item_habits -> {
                    findNavController(R.id.my_nav_host_fragment).navigate(NavGraphDirections.actionGlobalHabitFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.item_discover -> {
                    findNavController(R.id.my_nav_host_fragment).navigate(NavGraphDirections.actionGlobalDiscoverFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.item_profile -> {
                    findNavController(R.id.my_nav_host_fragment).navigate(NavGraphDirections.actionGlobalProfileFragment())
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }
}
