package com.cleo.myha.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleo.myha.R
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.ItemProfilePostBinding


class DiscoverAdapter(val onClickListener: OnClickListener): ListAdapter<Posts, DiscoverAdapter.DiscoverViewHolder>(DiscoverDiffCallBack()) {


    inner class DiscoverViewHolder(val binding: ItemProfilePostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Posts){
            binding.textView3.text = item.title

            Glide.with(itemView.context)
                .load(item.photo)
//                .error(R.drawable.ic_other1)
                .into(binding.imageView3)

            binding.imageView3.setOnClickListener {
                onClickListener.onClick(item)
            }

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

    class OnClickListener(val clickListener: (post: Posts) -> Unit) {
        fun onClick(post: Posts) = clickListener(post)
    }
}