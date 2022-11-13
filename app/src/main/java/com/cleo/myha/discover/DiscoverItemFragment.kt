package com.cleo.myha.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cleo.myha.databinding.FragmentDiscoverItemBinding
import com.cleo.myha.home.HomeViewModel
import com.cleo.myha.profile.ProfilePostAdapter

class DiscoverItemFragment : Fragment() {


//    private val viewModel: DiscoverViewModel by lazy {
//        ViewModelProvider(this)[DiscoverViewModel::class.java]
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiscoverItemBinding.inflate(inflater, container, false)

        binding.discoverPostRecycler.apply{
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
        val viewModel = ViewModelProvider(this)[DiscoverViewModel::class.java]
        val adapter = DiscoverAdapter(viewModel)
        binding.discoverPostRecycler.adapter = adapter

        viewModel.allPost.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })



//        fun postData(){
//
//            FirebaseFirestore.getInstance().collection("posts")
//                .get()
//                .addOnSuccessListener { documents ->
//                    for(document in documents) {
//                        val user = documents.toObjects(Posts::class.java)
//                        user.sortBy {
//                            it.lastUpdatedTime
//                        }
//                        binding.discoverPostRecycler.adapter = context?.let { ProfilePostAdapter(it, user) }
//                    }
//                }
//                .addOnFailureListener {
//                    Log.d("Cleooo", "get fail")}
//        }
//
//        postData()

        return binding.root
    }
}