package com.cleo.myha.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cleo.myha.data.Category
import com.cleo.myha.databinding.FragmentDiscoverItemBinding

class DiscoverItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDiscoverItemBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[DiscoverViewModel::class.java]

        binding.discoverPostRecycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }

        val adapter = DiscoverAdapter(
            onClickListener = DiscoverAdapter.OnClickListener { Posts ->
                this.findNavController()
                    .navigate(DiscoverItemFragmentDirections.actionGlobalCommentFragment(Posts))
            }
        )

        binding.discoverPostRecycler.adapter = adapter

        val type = requireArguments().getString("post")
        if (type != null) {
            viewModel.setPost(type)
        }

        viewModel.allPost.observe(
            viewLifecycleOwner,
            Observer {
                adapter.submitList(it)
            }
        )

        return binding.root
    }

    companion object {

        fun newInstance(category: Category): DiscoverItemFragment {
            val fragment = DiscoverItemFragment()
            val args = Bundle()
            args.putString("post", category.type)
            fragment.arguments = args
            return fragment
        }
    }
}
