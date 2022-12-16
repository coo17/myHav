package com.cleo.myha.discover.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Category
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemDiscoverBinding

class CommunityAdapter(val onClickListener: OnClickListener, val viewModel: CommunityViewModel) :
    ListAdapter<Habits, CommunityAdapter.GroupViewHolder>(GroupDiffCallBack()) {

    inner class GroupViewHolder(private var binding: ItemDiscoverBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits) {

            binding.userTaskTitle.text = data.task
            binding.layoutCommunity.setBackgroundResource(
                when (data.category) {
                    Category.Health.type -> R.drawable.cart_rounded_health
                    Category.Workout.type -> R.drawable.cart_rounded_workout
                    Category.Reading.type-> R.drawable.cart_rounded_reading
                    Category.Learning.toString() -> R.drawable.cart_rounded_learning
                    Category.General.toString() -> R.drawable.cart_rounded_general
                    else -> {
                        R.drawable.cart_rounded_other
                    }
                }
            )

            binding.imageView4.setImageResource(
                when (data.category) {
                    Category.Health.type-> R.drawable.group_health
                    Category.Workout.type-> R.drawable.group_workout
                    Category.Reading.type-> R.drawable.group_reading
                    Category.Learning.type-> R.drawable.ic_learning
                    Category.General.type -> R.drawable.group_other
                    else -> {
                        R.drawable.group_other
                    }
                }
            )

            binding.imageView4.setOnClickListener {
                onClickListener.onClick(data)
            }
        }
    }

    class GroupDiffCallBack : DiffUtil.ItemCallback<Habits>() {
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
                ),
                parent, false
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
