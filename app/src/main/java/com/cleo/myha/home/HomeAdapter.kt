package com.cleo.myha.home



import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemTodayTasksBinding
import java.util.*


class HomeAdapter(val onClickListener: HomeListener, val viewModel: HomeViewModel): ListAdapter<Habits, HomeAdapter.TaskViewHolder>(HomeDiffCallBack()) {

//    private var isSelectedAll = false

//    val checkBoxes = ArrayList<CheckBox>()
//    checkBoxes.checkAll(true)
//    checkBoxse.checkAll(false)
//
//    fun selectAll() {
//        Log.e("onClickSelectAll", "yes")
//        isSelectedAll = true
//        notifyDataSetChanged()
//    }


    inner class TaskViewHolder(private var binding:ItemTodayTasksBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits){

            val time = data.reminder
            Calendar.getInstance().timeInMillis = time

            binding.textTask.text = data.task
            binding.textTaskReminder.text = toFormat(time)
            binding.textTaskTimer.text = "${data.timer} minutes"


//            binding.checkBox.setOnClickListener {
//                viewModel.sendCompletedTask(data)
//            }

            binding.checkBox.isChecked = viewModel.completedList.get(data.id)  ?: false
            Log.d("Viccc", "id = ${data.id}  is ${viewModel.completedList.get(data.id)}")


            binding.checkBox.setOnClickListener {
                onClickListener.onClick(binding.checkBox.isChecked)
               viewModel.sendCompletedTask(data, true)
                itemView.isClickable = false

            }

//            if(data.mode == 0){
//                binding.dailyLayout.background = ResourcesCompat.getDrawable(itemView.resources,R.drawable.ic_reading, null)
//            }
//            else{
//                binding.dailyLayout.background = ResourcesCompat.getDrawable(itemView.resources,R.drawable.cart_rounded_workout, null)
//            }



            binding.dailyLayout.setBackgroundResource(
                when (data.category) {
                    "health" -> R.drawable.cart_rounded_workout
                    "workout" -> R.drawable.cart_rounded_workout
                    "reading" -> R.drawable.cart_rounded_reading
                    "learning" -> R.drawable.cart_rounded_learning
                    "general" -> R.drawable.cart_rounded_general
                    else -> {
                        R.drawable.cart_rounded_other
                    }
                }
            )
//            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
//
//                if(isChecked == true){
//                    onClickListener.onClick(true)
//                    viewModel.sendCompletedTask(data, true)
//                    buttonView.isClickable = false
//
//                }else{
//                    buttonView.isClickable = true
//                    onClickListener.onClick(false)
//                }
//            }


        }

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

    class HomeListener(val checkListener:(checkbox: Boolean) -> Unit){
        fun onClick(check: Boolean) = checkListener(check)
    }
}

