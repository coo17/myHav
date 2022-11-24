package com.cleo.myha.habits.single

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemSingleTaskBinding
import com.cleo.myha.habits.group.GroupAdapter
import com.cleo.myha.habits.group.GroupViewModel
import com.google.android.material.shape.CornerFamily


class SingleAdapter() : ListAdapter<Habits, SingleAdapter.SingleViewHolder>(SingleDiffCallBack()) {

    class SingleViewHolder(private var binding: ItemSingleTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits) {

            binding.userTaskTitle.text = data.task
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

            val radius = 50.0f
            binding.imageView.shapeAppearanceModel = binding.imageView.shapeAppearanceModel
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, radius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                .setTopLeftCorner(CornerFamily.ROUNDED,radius)
                .setBottomRightCorner(CornerFamily.ROUNDED,radius)
                .build()

            binding.imageView.setImageResource(
                when (data.category) {
                    "health" -> R.drawable.lion
                    "workout" -> R.drawable.icon_workout
                    "reading" -> R.drawable.icon_reading
                    "learning" -> R.drawable.icon_learning
                    "general" -> R.drawable.icon_smilingface
                    else -> {
                        R.drawable.icon_heart
                    }
                }
            )

        }
    }

    class SingleDiffCallBack : DiffUtil.ItemCallback<Habits>() {
        override fun areItemsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
       return SingleViewHolder(
                    ItemSingleTaskBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
            )
        )
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        val singleTaskData = getItem(position)
        holder.bind(singleTaskData)
    }
//
//    class OnClickListener(val clickListener: (habit: Habits) -> Unit) {
//        fun onClick(habit: Habits) = clickListener(habit)
//    }
}