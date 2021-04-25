package com.android.pxapp.ui.message

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.pxapp.data.model.Message
import com.android.pxapp.databinding.ItemMessageBinding
import java.text.SimpleDateFormat

class MessageListAdapter(private val checkClickListener :(Message, Boolean) -> Unit) : ListAdapter<Message, MessageListAdapter.MessageViewHolder>(MessageDiffCallback()) {
    var viewState: ToolbarState = ToolbarState.DefaultViewState
    var selectAll: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message, viewState, checkClickListener, selectAll)

    }

    class MessageViewHolder(private val binding: ItemMessageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            message: Message,
            viewState: ToolbarState,
            checkClickListener: (Message, Boolean) -> Unit,
            selectAll: Boolean
        ) {
            binding.tvTitle.text = message.title
            val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            val date = java.util.Date(message.ts.toLong())
            binding.tvTime.text = sdf.format(date).toString()
            binding.tvMessage.text = message.msg
            if(viewState is ToolbarState.DefaultViewState) {
                binding.cbSelect.isChecked = false
                binding.cbSelect.visibility = GONE
            } else {
                binding.cbSelect.visibility = VISIBLE
                binding.cbSelect.isChecked = selectAll
            }
            binding.cbSelect.setOnCheckedChangeListener { _, isChecked ->
                checkClickListener(message, isChecked)
            }


        }

        companion object {
            fun from(parent: ViewGroup): MessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMessageBinding.inflate(layoutInflater, parent, false)
                return MessageViewHolder(binding)
            }

        }

    }

}

class MessageDiffCallback: DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

}

