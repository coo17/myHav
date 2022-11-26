package com.cleo.myha.habits.single

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
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
import kotlinx.coroutines.joinAll


class SingleAdapter() : ListAdapter<Habits, SingleAdapter.SingleViewHolder>(SingleDiffCallBack()) {

    class SingleViewHolder(private var binding: ItemSingleTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits) {



            binding.userTaskCategory.text = data.category
            binding.userTaskTitle.text = data.task
            binding.userTaskDuration.text = data.duration

            binding.imageBackground.setBackgroundResource(
                when (data.category) {
                    "health" -> R.drawable.ic_reading
                    "workout" -> R.drawable.cart_rounded_workout
                    "reading" -> R.drawable.ic_reading
                    "learning" -> R.drawable.cart_rounded_learning
                    "general" -> R.drawable.cart_rounded_general
                    else -> {
                        R.drawable.cart_rounded_other
                    }
                }
            )

//            binding.layoutTask.foreground = ResourcesCompat.getDrawable(itemView.resources,R.drawable.ic_reading, null)

//            val radius = 50.0f
//            binding.imageView.shapeAppearanceModel = binding.imageView.shapeAppearanceModel
//                .toBuilder()
//                .setTopRightCorner(CornerFamily.ROUNDED, radius)
//                .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
//                .setTopLeftCorner(CornerFamily.ROUNDED,radius)
//                .setBottomRightCorner(CornerFamily.ROUNDED,radius)
//                .build()

//            binding.imageView.setImageResource(
//                when (data.category) {
//                    "health" -> R.drawable.lion
//                    "workout" -> R.drawable.icon_workout
//                    "reading" -> R.drawable.icon_reading
//                    "learning" -> R.drawable.icon_learning
//                    "general" -> R.drawable.icon_smilingface
//                    else -> {
//                        R.drawable.icon_heart
//                    }
//                }
//            )

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