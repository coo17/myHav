package com.cleo.myha.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Category
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemTodayTasksBinding
import java.util.*

class HomeAdapter(val onClickListener: HomeListener, val viewModel: HomeViewModel) :
    ListAdapter<Habits, HomeAdapter.TaskViewHolder>(HomeDiffCallBack()) {

    inner class TaskViewHolder(private var binding: ItemTodayTasksBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits) {

            val time = data.reminder
            Calendar.getInstance().timeInMillis = time

            binding.textTask.text = data.task
            binding.textTaskReminder.text = toFormat(time)
            binding.textTaskTimer.text = "${data.timer} minutes"

            binding.checkBox.isChecked = viewModel.completedList.get(data.id) ?: false
            binding.checkBox.setOnClickListener {
                onClickListener.onClick(binding.checkBox.isChecked)
                viewModel.sendCompletedTask(data, true)
                itemView.isClickable = false
            }

            binding.dailyLayout.setBackgroundResource(
                when (data.category) {
                    Category.Health.type -> R.drawable.cart_rounded_workout
                    Category.Workout.type -> R.drawable.cart_rounded_workout
                    Category.Reading.type -> R.drawable.cart_rounded_reading
                    Category.Learning.type -> R.drawable.cart_rounded_learning
                    Category.General.type -> R.drawable.cart_rounded_general
                    else -> {
                        R.drawable.cart_rounded_other
                    }
                }
            )
        }

        private fun toFormat(millis: Long): String {
            val hours = millis / (1000 * 60 * 60)
            val minutes = millis / (1000 * 60) - (hours * 60)
            val newHour = if (hours == -1L) {
                "00"
            } else hours

            val newMinutes = if (minutes == -1L || minutes == 0L) {
                "00"
            } else minutes
            return "$newHour:$newMinutes"
        }
    }

    class HomeDiffCallBack : DiffUtil.ItemCallback<Habits>() {
        override fun areItemsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTodayTasksBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val todayTaskData = getItem(position)
        holder.bind(todayTaskData)
    }

    class HomeListener(val checkListener: (checkbox: Boolean) -> Unit) {
        fun onClick(check: Boolean) = checkListener(check)
    }
}
