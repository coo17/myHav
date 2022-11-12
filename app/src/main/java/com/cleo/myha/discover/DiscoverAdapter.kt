package com.cleo.myha.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.ItemProfilePostBinding


class DiscoverAdapter(val viewModel: DiscoverViewModel): ListAdapter<Posts, DiscoverAdapter.DiscoverViewHolder>(DiscoverDiffCallBack()) {


    class DiscoverViewHolder(val binding: ItemProfilePostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Posts){
            binding.textView3.text = item.title
            Glide.with(itemView.context)
                .load(item.photo)
                .into(binding.imageView3)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverViewHolder {
        return DiscoverViewHolder(ItemProfilePostBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DiscoverViewHolder, position: Int) {
        val userPost = getItem(position)
        holder.bind(userPost)
    }


    class DiscoverDiffCallBack : DiffUtil.ItemCallback<Posts>(){
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }
    }

}