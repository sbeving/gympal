package com.example.gym_pal.ui.coach

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gym_pal.R
import com.example.gym_pal.data.model.CoachConversation
import io.noties.markwon.Markwon
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.image.ImagesPlugin
import java.text.SimpleDateFormat
import java.util.Locale

class ChatMessageAdapter : ListAdapter<CoachConversation, ChatMessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_chat_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val incomingLayout = itemView.findViewById<View>(R.id.incoming_layout)
        private val outgoingLayout = itemView.findViewById<View>(R.id.outgoing_layout)
        private val tvIncomingMessage = itemView.findViewById<TextView>(R.id.tv_incoming_message)
        private val tvOutgoingMessage = itemView.findViewById<TextView>(R.id.tv_outgoing_message)
        private val tvIncomingTime = itemView.findViewById<TextView>(R.id.tv_incoming_time)
        private val tvOutgoingTime = itemView.findViewById<TextView>(R.id.tv_outgoing_time)
        
        // Initialize Markwon with table and image support for rich markdown rendering
        private val markwon = Markwon.builder(itemView.context)
            .usePlugin(ImagesPlugin.create())
            .usePlugin(TablePlugin.create(itemView.context))
            .build()
        
        fun bind(message: CoachConversation) {
            val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
            val formattedTime = timeFormat.format(message.timestamp)
            
            if (message.isUserMessage) {
                // Show outgoing message (from user)
                outgoingLayout.visibility = View.VISIBLE
                incomingLayout.visibility = View.GONE
                tvOutgoingMessage.text = message.message
                tvOutgoingTime.text = formattedTime
            } else {
                // Show incoming message (from AI) with markdown rendering
                incomingLayout.visibility = View.VISIBLE
                outgoingLayout.visibility = View.GONE
                
                // Use Markwon to render markdown text
                markwon.setMarkdown(tvIncomingMessage, message.message)
                tvIncomingTime.text = formattedTime
            }
        }
    }

    private class MessageDiffCallback : DiffUtil.ItemCallback<CoachConversation>() {
        override fun areItemsTheSame(oldItem: CoachConversation, newItem: CoachConversation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CoachConversation, newItem: CoachConversation): Boolean {
            return oldItem == newItem
        }
    }
}
