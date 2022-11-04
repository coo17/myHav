package com.cleo.myha.habits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cleo.myha.R
import com.cleo.myha.data.Habits
import com.cleo.myha.databinding.ItemSingleTaskBinding


class HabitAdapter(): ListAdapter<Habits, HabitAdapter.SingleViewHolder>(SingleDiffCallBack()){

    class SingleViewHolder(private var binding: ItemSingleTaskBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Habits){

            binding.userTaskTitle.text = data.task

//            binding.layoutTask.setBackgroundColor(
//                when(data.category){
//                    "health" -> R.drawable.rounded_days
//                    "workout" -> R.color.light_grey
//                    "reading" -> R.color.slate_grey
//                    "learning" -> R.drawable.rounded_days
//                    "general" -> R.drawable.rounded_days
//                    else -> { R.drawable.icon_heart}
//                })


            binding.imageView.setImageResource(
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

    class SingleDiffCallBack: DiffUtil.ItemCallback<Habits>() {
        override fun areItemsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Habits, newItem: Habits): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        return SingleViewHolder(ItemSingleTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        val singleTaskData = getItem(position)
        holder.bind(singleTaskData)
        holder.itemView.setOnClickListener{
            Navigation.createNavigateOnClickListener(R.id.action_global_chatRoomsFragment).onClick(holder.itemView)
        }
    }


}