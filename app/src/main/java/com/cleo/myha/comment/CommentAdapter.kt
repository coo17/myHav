package com.cleo.myha.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleo.myha.data.CommentsInfo
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.ItemCommentBinding

import com.cleo.myha.discover.DiscoverViewModel

class CommentAdapter(val viewModel: CommentViewModel): ListAdapter<CommentsInfo, CommentAdapter.CommentViewHolder>(CommentDiffCallBack()) {


    inner class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommentsInfo){
            binding.textComment.text = item.content
            binding.textTime.text = item.createdTime
//            binding.avatarUser.setImageResource

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


    class CommentDiffCallBack : DiffUtil.ItemCallback<CommentsInfo>(){
        override fun areItemsTheSame(oldItem: CommentsInfo, newItem: CommentsInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CommentsInfo, newItem: CommentsInfo): Boolean {
            return oldItem == newItem
        }
    }

}