package com.cleo.myha.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.ItemProfilePostBinding

class ProfilePostAdapter(private val context: Context, private val users: List<Posts>) :
    RecyclerView.Adapter<ProfilePostAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: ItemProfilePostBinding) : RecyclerView.ViewHolder(itemView.root) {
        val userName: TextView = itemView.textView3
        val userImage: ImageView = itemView.imageView3
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemProfilePostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.userName.text = user.title
        Glide.with(holder.itemView.context)
            .load(user.photo)
            .into(holder.userImage)
    }

//    class CommentListener(val checkListener:(collection: Posts) -> Unit){
//        fun onClick(collection: Posts) = checkListener(collection)
//    }
}
