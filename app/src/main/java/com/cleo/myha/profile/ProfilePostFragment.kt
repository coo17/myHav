package com.cleo.myha.profile


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.FragmentProfilePostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class ProfilePostFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfilePostBinding.inflate(inflater, container, false)
        auth= FirebaseAuth.getInstance()

        val viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.profilePostRecycler.apply{
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }




        fun postData(){

            val authorEmail = auth.currentUser?.let {
                it.email
            }

            FirebaseFirestore.getInstance().collection("posts")
                .whereEqualTo("author", authorEmail)
                .get()
                .addOnSuccessListener { documents ->
                    for(document in documents) {
                        val user = documents.toObjects(Posts::class.java)
                        user.sortBy {
                            it.lastUpdatedTime
                        }
                        binding.profilePostRecycler.adapter = context?.let { ProfilePostAdapter(it, user) }
                    }
                }
                .addOnFailureListener {
                    Log.d("Cleooo", "get fail")}
        }

        postData()
        return binding.root
    }

}