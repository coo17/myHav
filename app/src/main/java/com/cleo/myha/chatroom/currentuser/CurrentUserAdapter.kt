package com.cleo.myha.chatroom.currentuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleo.myha.R
import com.cleo.myha.data.MessagesInfo
import com.cleo.myha.data.User
import com.cleo.myha.databinding.ItemCurrentUserBinding
import com.google.android.material.shape.CornerFamily


class CurrentUserAdapter() :
    ListAdapter<User, CurrentUserAdapter.CurrentUserViewHolder>(UserDiffCallBack()) {

    class CurrentUserViewHolder(private var binding: ItemCurrentUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User){

            val radius = 50.0f
            binding.userPhoto.shapeAppearanceModel = binding.userPhoto.shapeAppearanceModel
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .setTopLeftCorner(CornerFamily.ROUNDED,radius)
                .setBottomRightCorner(CornerFamily.ROUNDED,radius)
                .build()

            binding.userName.text = item.name

            Glide.with(itemView.context)
                .load(item.photo)
                .placeholder(R.drawable.lion)
                .into(binding.userPhoto)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentUserViewHolder {
        return CurrentUserViewHolder(
            ItemCurrentUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrentUserViewHolder, position: Int) {
        val currentUserData = getItem(position)
        holder.bind(currentUserData)
    }

    class UserDiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
}