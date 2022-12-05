package com.cleo.myha.discover

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleo.myha.R
import com.cleo.myha.comment.blocklist.BlockDialogArgs
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.ItemProfilePostBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class DiscoverAdapter(val onClickListener: OnClickListener): ListAdapter<Posts, DiscoverAdapter.DiscoverViewHolder>(DiscoverDiffCallBack()) {

    val storageReference = FirebaseStorage.getInstance()


    inner class DiscoverViewHolder(val binding: ItemProfilePostBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Posts){
            binding.textView3.text = item.title

            item.photo?.let { it ->
                storageReference.getReferenceFromUrl(it).downloadUrl.addOnSuccessListener {imgPhoto->
                    Glide.with(itemView.context)
                        .load(imgPhoto)
                        .error(R.drawable.man)
                        .into(binding.imageView3)
                }

            }


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