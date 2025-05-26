package com.example.gym_pal.ui.coach

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_pal.R
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter(private val messages: MutableList<Message> = mutableListOf()) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardMessage: CardView = view.findViewById(R.id.card_message)
        val message: TextView = view.findViewById(R.id.tv_message)
        val timestamp: TextView = view.findViewById(R.id.tv_timestamp)
        val layout: ConstraintLayout = view as ConstraintLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.message.text = message.text
        
        // Format timestamp
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        holder.timestamp.text = sdf.format(message.timestamp)
        
        // Adjust layout based on message source (user or AI)
        val constraintSet = ConstraintSet()
        constraintSet.clone(holder.layout)
        
        if (message.isFromUser) {
            // User message - right aligned with primary color
            holder.cardMessage.setCardBackgroundColor(holder.itemView.context.getColor(R.color.primary))
            holder.message.setTextColor(holder.itemView.context.getColor(R.color.white))
            constraintSet.clear(R.id.card_message, ConstraintSet.START)
            constraintSet.connect(
                R.id.card_message,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
                8
            )
            constraintSet.connect(
                R.id.card_message,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                64
            )
        } else {
            // AI message - left aligned with light color
            holder.cardMessage.setCardBackgroundColor(holder.itemView.context.getColor(R.color.surface))
            holder.message.setTextColor(holder.itemView.context.getColor(R.color.on_surface))
            constraintSet.clear(R.id.card_message, ConstraintSet.END)
            constraintSet.connect(
                R.id.card_message,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                8
            )
            constraintSet.connect(
                R.id.card_message,
                ConstraintSet.END,
                ConstraintSet.PARENT_ID,
                ConstraintSet.END,
                64
            )
        }
        
        // Also move timestamp to match message alignment
        if (message.isFromUser) {
            constraintSet.clear(R.id.tv_timestamp, ConstraintSet.START)
            constraintSet.connect(
                R.id.tv_timestamp,
                ConstraintSet.END,
                R.id.card_message,
                ConstraintSet.END,
                0
            )
        } else {
            constraintSet.clear(R.id.tv_timestamp, ConstraintSet.END)
            constraintSet.connect(
                R.id.tv_timestamp,
                ConstraintSet.START,
                R.id.card_message,
                ConstraintSet.START,
                0
            )
        }
        
        constraintSet.applyTo(holder.layout)
    }

    override fun getItemCount() = messages.size

    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
}
