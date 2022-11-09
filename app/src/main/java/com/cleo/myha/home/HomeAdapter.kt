package com.cleo.myha.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemTodayTasksBinding
import java.util.*


class HomeAdapter(val viewModel: HomeViewModel): ListAdapter<Habits, HomeAdapter.TaskViewHolder>(HomeDiffCallBack()) {


    inner class TaskViewHolder(private var binding:ItemTodayTasksBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits){

            val time = data.reminder
            Calendar.getInstance().timeInMillis = time

            binding.textTask.text = data.task
            binding.textTaskReminder.text = toFormat(time)
            binding.imageHabit.setImageResource(
                when(data.category){
                    "health" -> R.drawable.icon_health
                    "workout" -> R.drawable.icon_workout
                    "reading" -> R.drawable.icon_reading
                    "learning" -> R.drawable.icon_learning
                    "general" -> R.drawable.icon_smilingface
                    else -> { R.drawable.icon_heart}
                })

//            binding.checkBox.setOnClickListener {
//                viewModel.sendCompletedTask(data)
//            }
//            binding.checkBox.isChecked(要回來做的->歷史today task)
            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked == true){
                    viewModel.sendCompletedTask(data)
                    buttonView.isClickable = false
                }else{
                    false
                }
            }
        }

//        fun getStringFromLong(millis: Long): String? {
//            val sdf = SimpleDateFormat("hh:mm a")
//            val dt: Date = Date(millis)
//            return sdf.format(dt)
//        }

        fun toFormat(millis: Long): String? {
            val hours = millis/(1000*60*60)
            val minutes = millis/(1000*60) - (hours*60)
            val newHour = if (hours == -1L){
                "00"
            }else hours

            val newMinutes = if (minutes == -1L || minutes == 0L ){
                "00"
            }else minutes
            return "${newHour}:${newMinutes}"
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


