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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class ProfilePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfilePostBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        binding.profilePostRecycler.apply{
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }




        fun postData(){

            FirebaseFirestore.getInstance().collection("posts")
                .whereEqualTo("author","IU@gmail.com")
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