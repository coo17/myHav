package com.cleo.myha.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.CommentsInfo
import com.cleo.myha.databinding.ItemCommentBinding
import convertToTime


class CommentAdapter(val viewModel: CommentViewModel): ListAdapter<CommentsInfo, CommentAdapter.CommentViewHolder>(CommentDiffCallBack()) {



    inner class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommentsInfo){

            val currentTime = item.updatedTime
            val createdTime = currentTime?.toDate()?.let { (it.time) }

            binding.textComment.text = item.content

            if (createdTime != null) {
                binding.textTime.text = createdTime.convertToTime()
            }

            binding.userName.text = item.userName
            binding.avatarUser.setImageResource(R.drawable.lion)

//            val radius = 50.0f
//            binding.imageView.shapeAppearanceModel = binding.imageView.shapeAppearanceModel
//                .toBuilder()
//                .setTopRightCorner(CornerFamily.ROUNDED, radius)
//                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
//                .setTopLeftCorner(CornerFamily.ROUNDED,radius)
//                .setBottomRightCorner(CornerFamily.ROUNDED,radius)
//                .build()

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {
        return CommentViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent, false))
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