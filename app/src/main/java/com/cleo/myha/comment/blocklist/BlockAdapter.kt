package com.cleo.myha.comment.blocklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.data.User
import com.cleo.myha.databinding.ItemBlockUserBinding

class BlockAdapter : ListAdapter<User, BlockAdapter.BlockViewHolder>(BlockDiffCallBack()) {

    inner class BlockViewHolder(val binding: ItemBlockUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {

            binding.userName.text = item.name
            binding.userEmail.text = item.email
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BlockViewHolder {
        return BlockViewHolder(
            ItemBlockUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        val userPost = getItem(position)
        holder.bind(userPost)
    }

    class BlockDiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
