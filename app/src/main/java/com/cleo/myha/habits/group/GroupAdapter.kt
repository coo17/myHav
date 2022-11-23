package com.cleo.myha.habits.group

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.data.Posts
import com.cleo.myha.databinding.ItemGroupTaskBinding
import com.cleo.myha.discover.DiscoverAdapter
import com.cleo.myha.discover.DiscoverViewModel

class GroupAdapter(val onClickListener: OnClickListener, val viewModel: GroupViewModel): ListAdapter<Habits, GroupAdapter.GroupViewHolder>(GroupDiffCallBack()) {

    inner class GroupViewHolder(private var binding: ItemGroupTaskBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits){

            binding.groupTaskTitle.text = data.task
            binding.layoutTask.setBackgroundResource(
                when (data.category) {
                    "health" -> R.drawable.cart_rounded_health
                    "workout" -> R.drawable.cart_rounded_workout
                    "reading" -> R.drawable.cart_rounded_reading
                    "learning" -> R.drawable.cart_rounded_learning
                    "general" -> R.drawable.cart_rounded_general
                    else -> {
                        R.drawable.cart_rounded_other
                    }
                }
            )

            binding.imageView.setImageResource(
                when (data.category) {
                    "health" -> R.drawable.icon_health
                    "workout" -> R.drawable.icon_workout
                    "reading" -> R.drawable.icon_reading
                    "learning" -> R.drawable.icon_learning
                    "general" -> R.drawable.icon_smilingface
                    else -> {
                        R.drawable.icon_heart
                    }
                }
            )

            binding.root.setOnClickListener {
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
        return GroupViewHolder(ItemGroupTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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