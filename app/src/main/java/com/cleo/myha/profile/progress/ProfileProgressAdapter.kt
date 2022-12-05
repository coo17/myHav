package com.cleo.myha.profile.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.data.HabitTracker
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemDiscoverBinding
import com.cleo.myha.databinding.ItemTrackerCategoryBinding

class ProfileProgressAdapter: ListAdapter<HabitTracker, ProfileProgressAdapter.ProgressViewHolder>(ProgressDiffCallBack()) {

    inner class ProgressViewHolder(private var binding: ItemTrackerCategoryBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: HabitTracker) {
            val habitSize = data.totalSize
            binding.category.text = data.category
            binding.totalHabitCreated.text = "${habitSize} habits created"


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        return ProgressViewHolder(
            ItemTrackerCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    class ProgressDiffCallBack: DiffUtil.ItemCallback<HabitTracker>() {
        override fun areItemsTheSame(oldItem: HabitTracker, newItem: HabitTracker): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HabitTracker, newItem: HabitTracker): Boolean {
            return oldItem == newItem
        }

    }
}