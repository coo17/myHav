package com.cleo.myha.discover




import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cleo.myha.databinding.FragmentDiscoverBinding
import com.google.android.material.tabs.TabLayoutMediator


class DiscoverFragment : Fragment() {

    private lateinit var binding: FragmentDiscoverBinding
    private val tabTitles = arrayListOf("all", "health", "workout", "learning", "reading", "general")


    private val viewModel: DiscoverViewModel by lazy {
        ViewModelProvider(this)[DiscoverViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDiscoverBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = DiscoverViewPagerAdapter(childFragmentManager, lifecycle)

        setUpTabLayoutWithViewPager()

//        binding.recyclerDiscoverPost.apply {
//            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        }
//
//
////        val adapter = DiscoverAdapter()
////        Log.d("adapter", "${adapter}")
////        viewModel.allPost.observe(viewLifecycleOwner, Observer {
////            adapter.submitList(it)
////        })
//
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
//                        binding.recyclerDiscoverPost.adapter = context?.let { ProfilePostAdapter(it, user) }
//                    }
//                }
//                .addOnFailureListener {
//                    Log.d("Cleooo", "get fail")}
//        }
//
//        postData()
//
        return binding.root
    }

    private fun setUpTabLayoutWithViewPager() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

}