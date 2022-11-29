package com.cleo.myha.habits.single

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemSingleTaskBinding
import convertDurationToDate


class SingleAdapter() : ListAdapter<Habits, SingleAdapter.SingleViewHolder>(SingleDiffCallBack()) {

    class SingleViewHolder(private var binding: ItemSingleTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits) {



            binding.userTaskCategory.text = data.category
            binding.userTaskTitle.text = data.task
            binding.startedDate.text = data.startedDate.convertDurationToDate()
            binding.endDate.text = "~ ${data.endDate.convertDurationToDate()}"

            binding.imageBackground.setBackgroundResource(
                when (data.category) {
                    "Health" -> R.drawable.group_health
                    "Workout" -> R.drawable.group_workout
                    "Reading" -> R.drawable.ic_reading
                    "Learning" -> R.drawable.ic_learning
                    "General" -> R.drawable.ic_other
                    else -> {
                        R.drawable.ic_other
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