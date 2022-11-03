package com.cleo.myha.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemTodayTasksBinding
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class HomeAdapter(): ListAdapter<Habits, HomeAdapter.TaskViewHolder>(HomeDiffCallBack()) {

    class TaskViewHolder(private var binding:ItemTodayTasksBinding): RecyclerView.ViewHolder(binding.root) {

//        fun main(args: Array<String>) {
//            try {
//                val _24HourTime = "22:15"
//                val _24HourSDF = SimpleDateFormat("HH:mm")
//                val _12HourSDF = SimpleDateFormat("hh:mm a")
//                val _24HourDt = _24HourSDF.parse(_24HourTime)
//                System.out.println(_24HourDt)
//                println(_12HourSDF.format(_24HourDt))
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }


        fun bind(data: Habits){

            val time = data.reminder
            Calendar.getInstance().timeInMillis = time

            binding.textTask.text = data.task
            binding.textTaskReminder.text = getStringFromLong(time)
            binding.imageHabit.setImageResource(
                when(data.category){
                    "health" -> R.drawable.icon_health
                    "workout" -> R.drawable.icon_workout
                    "reading" -> R.drawable.icon_reading
                    "learning" -> R.drawable.icon_learning
                    "general" -> R.drawable.icon_smilingface
                    else -> { R.drawable.icon_heart}
                })

        }

        fun getStringFromLong(millis: Long): String? {
            val sdf = SimpleDateFormat("hh:mm a")
            val dt: Date = Date(millis)
            return sdf.format(dt)
        }
    }

    class HomeDiffCallBack: DiffUtil.ItemCallback<Habits>() {
        override fun areItemsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTodayTasksBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val todayTaskData = getItem(position)
        holder.bind(todayTaskData)
    }
}


