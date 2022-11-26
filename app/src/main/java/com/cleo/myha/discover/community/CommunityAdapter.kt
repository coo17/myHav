package com.cleo.myha.discover.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.ItemDiscoverBinding
import com.cleo.myha.discover.DiscoverAdapter
import com.cleo.myha.discover.DiscoverViewModel
import com.google.android.material.shape.CornerFamily

class CommunityAdapter(val onClickListener: OnClickListener, val viewModel: CommunityViewModel): ListAdapter<Habits, CommunityAdapter.GroupViewHolder>(GroupDiffCallBack()) {


   inner class GroupViewHolder(private var binding: ItemDiscoverBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: Habits){

            val radius = 50.0f
            binding.imageView4.shapeAppearanceModel = binding.imageView4.shapeAppearanceModel
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                .setBottomRightCorner(CornerFamily.ROUNDED, radius)
                .build()


            binding.userTaskTitle.text = data.task
            binding.layoutCommunity.setBackgroundResource(
                when(data.category){
                    "health" -> R.drawable.cart_rounded_health
                    "workout" -> R.drawable.cart_rounded_workout
                    "reading" -> R.drawable.cart_rounded_reading
                    "learning" -> R.drawable.cart_rounded_learning
                    "general" -> R.drawable.cart_rounded_general
                    else -> { R.drawable.cart_rounded_other}
                })

            binding.imageView4.setImageResource(
                when(data.category){
                    "health" -> R.drawable.ic_reading
                    "workout" -> R.drawable.ic_reading
                    "reading" -> R.drawable.ic_reading
                    "learning" -> R.drawable.ic_reading
                    "general" -> R.drawable.ic_reading
                    else -> { R.drawable.icon_heart}
                })

            binding.imageView4.setOnClickListener {
                onClickListener.onClick(data)
            }

        }
    }

    class GroupDiffCallBack: DiffUtil.ItemCallback<Habits>() {
        override fun areItemsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            ItemDiscoverBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val groupTaskData = getItem(position)
        holder.bind(groupTaskData)
    }

    class OnClickListener(val clickListener: (habits: Habits) -> Unit) {
        fun onClick(habits: Habits) = clickListener(habits)
    }
}