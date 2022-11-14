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
import com.cleo.myha.databinding.ItemDiscoverBinding

class CommunityAdapter(): ListAdapter<Habits, CommunityAdapter.GroupViewHolder>(GroupDiffCallBack()) {

    class GroupViewHolder(private var binding: ItemDiscoverBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: Habits){

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
                    "health" -> R.drawable.icon_health
                    "workout" -> R.drawable.icon_workout
                    "reading" -> R.drawable.icon_reading
                    "learning" -> R.drawable.icon_learning
                    "general" -> R.drawable.icon_smilingface
                    else -> { R.drawable.icon_heart}
                })

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
        holder.itemView.setOnClickListener{
            Navigation.createNavigateOnClickListener(R.id.action_global_taskDialog).onClick(holder.itemView)
        }
    }
}