package com.cleo.myha.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.ItemCommentBinding

import com.cleo.myha.discover.DiscoverViewModel

class CommentAdapter(val viewModel: DiscoverViewModel): ListAdapter<Posts, CommentAdapter.CommentViewHolder>(CommentDiffCallBack()) {


    inner class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Posts){

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        return CommentViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val userPost = getItem(position)
        holder.bind(userPost)

    }


    class CommentDiffCallBack : DiffUtil.ItemCallback<Posts>(){
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }
    }

}