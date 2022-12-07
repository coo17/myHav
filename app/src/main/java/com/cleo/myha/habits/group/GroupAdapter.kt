package com.cleo.myha.habits.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemGroupTaskBinding
import convertDurationToDate

class GroupAdapter(val onClickListener: OnClickListener, val viewModel: GroupViewModel) : ListAdapter<Habits, GroupAdapter.GroupViewHolder>(GroupDiffCallBack()) {

    inner class GroupViewHolder(private var binding: ItemGroupTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits) {

            binding.groupTaskTitle.text = data.task
            binding.groupTaskCategory.text = data.category

            binding.startedDate.text = data.startedDate.convertDurationToDate()
            binding.endDate.text = "~ ${data.endDate.convertDurationToDate()}"

            binding.imageBackground.setBackgroundResource(
                when (data.category) {
                    "Health" -> R.drawable.group_health
                    "Workout" -> R.drawable.group_workout
                    "Reading" -> R.drawable.group_reading
                    "Learning" -> R.drawable.cart_rounded_learning
                    "General" -> R.drawable.group_other
                    else -> {
                        R.drawable.group_other
                    }
                }
            )

            binding.root.setOnClickListener {
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
        return GroupViewHolder(ItemGroupTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val groupTask = getItem(position)
        holder.bind(groupTask)
//        holder.itemView.setOnClickListener{
//            Navigation.createNavigateOnClickListener(R.id.action_global_chatRoomsFragment).onClick(holder.itemView)
//        }
    }

    class OnClickListener(val clickListener: (habit: Habits) -> Unit) {
        fun onClick(habit: Habits) = clickListener(habit)
    }
}
