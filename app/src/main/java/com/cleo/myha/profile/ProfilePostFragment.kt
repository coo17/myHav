package com.cleo.myha.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cleo.myha.NavGraphDirections
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.FragmentProfilePostBinding
import com.cleo.myha.discover.DiscoverAdapter
import com.cleo.myha.discover.DiscoverItemFragmentDirections
import com.cleo.myha.util.AUTHOR_FILED_POSTS
import com.cleo.myha.util.POSTS_PATH
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfilePostFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfilePostBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.profilePostRecycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        binding.createPost.setOnClickListener {
            findNavController().navigate(NavGraphDirections.actionGlobalPublishFragment())
        }

        fun postData() {

            val authorEmail = auth.currentUser?.email

            FirebaseFirestore.getInstance().collection(POSTS_PATH)
                .whereEqualTo(AUTHOR_FILED_POSTS, authorEmail)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val user = documents.toObjects(Posts::class.java)
                        user.sortBy {
                            it.lastUpdatedTime
                        }
                        val adapter = DiscoverAdapter(
                            onClickListener = DiscoverAdapter.OnClickListener { Posts ->
                                this.findNavController()
                                    .navigate(DiscoverItemFragmentDirections.actionGlobalCommentFragment(Posts))
                            }
                        )
                        binding.profilePostRecycler.adapter = adapter
                        adapter.submitList(user)
                    }
                }
                .addOnFailureListener {

                }
        }

        postData()
        return binding.root
    }
}
