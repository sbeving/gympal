package com.example.gym_pal.ui.coach

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_pal.R
import com.example.gym_pal.data.model.CoachConversation
import com.example.gym_pal.databinding.ItemMessageBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CoachConversationAdapter : ListAdapter<CoachConversation, CoachConversationAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
        
        fun bind(item: CoachConversation) {
            binding.apply {
                tvMessage.text = item.message
                tvTimestamp.text = dateFormatter.format(item.timestamp)
                
                // Set message alignment based on who sent it
                if (item.isUserMessage) {
                    // User message (right-aligned)
                    cardMessage.setCardBackgroundColor(root.context.getColor(R.color.primary))
                    tvMessage.setTextColor(root.context.getColor(R.color.white))
                    cardMessage.layoutParams = (cardMessage.layoutParams as ViewGroup.MarginLayoutParams).apply {
                        marginEnd = 16
                        marginStart = 100
                    }
                } else {
                    // AI message (left-aligned)
                    cardMessage.setCardBackgroundColor(root.context.getColor(R.color.surface))
                    tvMessage.setTextColor(root.context.getColor(R.color.on_surface))
                    cardMessage.layoutParams = (cardMessage.layoutParams as ViewGroup.MarginLayoutParams).apply {
                        marginStart = 16
                        marginEnd = 100
                    }
                }
            }
        }
    }
    
    class MessageDiffCallback : DiffUtil.ItemCallback<CoachConversation>() {
        override fun areItemsTheSame(oldItem: CoachConversation, newItem: CoachConversation): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: CoachConversation, newItem: CoachConversation): Boolean {
            return oldItem == newItem
        }
    }
}
