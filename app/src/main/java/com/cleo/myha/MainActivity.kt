package com.cleo.myha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.cleo.myha.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


const val REQUEST_CODE_SIGN_IN = 0


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webClient_id))
            .requestEmail()
            .build()

        val signInClient = GoogleSignIn.getClient(this, options)

        binding.btnSignIn.setOnClickListener {
            signInClient.signInIntent.also{
                startActivityForResult(it, REQUEST_CODE_SIGN_IN)
            }
        }

        binding.btnSignout.setOnClickListener {
            signInClient.signOut()
                .addOnCompleteListener(this, OnCompleteListener {
                    signOutFun()
                })
        }



        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId){
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
                else -> {return@setOnItemSelectedListener false}
            }
        }

    }

    private fun googleAuthForFirebase(account: GoogleSignInAccount){
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity,"Successfully logged in", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_SIGN_IN){
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                googleAuthForFirebase(it)
            }

        }
    }

    private fun signOutFun(){
        Toast.makeText(this@MainActivity,"Successfully Signed Out", Toast.LENGTH_SHORT).show()
    }

}