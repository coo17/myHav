package com.cleo.myha.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cleo.myha.R
import com.cleo.myha.data.MessagesInfo
import com.cleo.myha.data.UserType
import com.cleo.myha.databinding.ItemReceivedMessageBinding
import com.cleo.myha.databinding.ItemSentMessageBinding
import convertToTime
import java.text.SimpleDateFormat
import java.util.*


private const val ITEM_SENT_MESSAGE = 0X00
private const val ITEM_RECEIVED_MESSAGE = 0X01



class ChatRoomAdapter : ListAdapter<UserType, RecyclerView.ViewHolder>(DiffCallBack()) {


    class SentMessageViewHolder(private var binding: ItemSentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(data: UserType.Sender) {
            val currentTime = data.user.textTime
            val createdTime = currentTime?.toDate()?.let { (it.time) }

            binding.textMessage.text = data.user.message

            if (createdTime != null) {
                binding.textDateTime.text = createdTime.convertToTime()
            }

        }
    }

    class ReceivedMessageViewHolder(private var binding: ItemReceivedMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserType.Receiver) {
            val currentTime = data.user.textTime
            val createdTime = currentTime?.toDate()?.let { (it.time) }

            binding.textMessage.text = data.user.message
            if (createdTime != null) {
                binding.textDateTime.text = createdTime.convertToTime()
            }

            Glide.with(itemView.context)
                .load(data.user.senderImage)
                .placeholder(R.drawable.lion)
                .into(binding.imageProfile)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_SENT_MESSAGE -> SentMessageViewHolder(
                ItemSentMessageBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            ITEM_RECEIVED_MESSAGE -> ReceivedMessageViewHolder(
                ItemReceivedMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {
            is SentMessageViewHolder -> {
                val data = getItem(position) as UserType.Sender
                holder.bind(data)
            }
            is ReceivedMessageViewHolder -> {
                val data = getItem(position) as UserType.Receiver
                holder.bind(data)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is UserType.Sender -> ITEM_SENT_MESSAGE
            is UserType.Receiver -> ITEM_RECEIVED_MESSAGE
        }
    }


    class DiffCallBack : DiffUtil.ItemCallback<UserType>() {
        override fun areItemsTheSame(oldItem: UserType, newItem: UserType): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserType, newItem: UserType): Boolean {
            return oldItem == newItem
        }
    }

}