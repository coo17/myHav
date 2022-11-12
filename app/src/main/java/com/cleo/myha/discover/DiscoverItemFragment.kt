package com.cleo.myha.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.FragmentDiscoverBinding
import com.cleo.myha.databinding.FragmentDiscoverItemBinding
import com.cleo.myha.databinding.FragmentProfileBinding
import com.cleo.myha.profile.ProfilePostAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore

class DiscoverItemFragment : Fragment() {


    private val viewModel: DiscoverViewModel by lazy {
        ViewModelProvider(this)[DiscoverViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiscoverItemBinding.inflate(inflater, container, false)

        binding.discoverPostRecycler.apply{
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }



        fun postData(){

            FirebaseFirestore.getInstance().collection("posts")
                .get()
                .addOnSuccessListener { documents ->
                    for(document in documents) {
                        val user = documents.toObjects(Posts::class.java)
                        user.sortBy {
                            it.lastUpdatedTime
                        }
                        binding.discoverPostRecycler.adapter = context?.let { ProfilePostAdapter(it, user) }
                    }
                }
                .addOnFailureListener {
                    Log.d("Cleooo", "get fail")}
        }

        postData()

        return binding.root
    }
}