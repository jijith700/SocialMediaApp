package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sma.liveler.R
import com.sma.liveler.interfaces.OnClickChatListener
import com.sma.liveler.vo.ChatMessage
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber

class ChatAdapter(
    private var context: Context,
    private var onClickChatListener: OnClickChatListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    private var chatMessage: ArrayList<ChatMessage> = ArrayList<ChatMessage>()
    var currentUserId: Int? = null


    override fun getItemCount(): Int {
        return chatMessage.size
    }

    // Determines the appropriate ViewType according to the sender of the message.
    override fun getItemViewType(position: Int): Int {
        return if (chatMessage[position].from_user == currentUserId) { // If the current user is the sender of the message
            VIEW_TYPE_MESSAGE_SENT
        } else { // If some other user sent the message
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_send_item, parent, false)
            return SentMessageHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_received_item, parent, false)
            return ReceivedMessageHolder(view)
        }
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(chatMessage[position])
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(chatMessage[position])
        }
    }

    private class SentMessageHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvMessage: TextView
        var tvTime: TextView

        fun bind(chatMessage: ChatMessage) {
            tvMessage.setText(chatMessage.content)
            // Format the stored timestamp into a readable String using method.
            tvTime.setText(chatMessage.created_at)
            Timber.d("Chat adapter " + chatMessage)
        }

        init {
            tvMessage =
                itemView.findViewById<View>(R.id.tvSendMessage) as TextView
            tvTime = itemView.findViewById<View>(R.id.tvTime) as TextView
        }
    }

    private class ReceivedMessageHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvMessage: TextView
        var tvTime: TextView
        var tvUserName: TextView
        var ivUser: CircleImageView
        fun bind(chatMessage: ChatMessage) {
            tvMessage.setText(chatMessage.content)
            // Format the stored timestamp into a readable String using method.
            tvTime.setText(chatMessage.created_at)
            tvUserName.setText(chatMessage.receiver_name)

            Timber.d("Chat adapter " + chatMessage)
        }

        init {
            tvMessage =
                itemView.findViewById<View>(R.id.tvReceivedMessage) as TextView
            tvTime = itemView.findViewById<View>(R.id.tvTime) as TextView
            tvUserName = itemView.findViewById<View>(R.id.tvSendUser) as TextView
            ivUser =
                itemView.findViewById<View>(R.id.ivUser) as CircleImageView
        }
    }

    fun updateChatMessage(chatMessage: ArrayList<ChatMessage>) {
        this.chatMessage = chatMessage
        notifyDataSetChanged()
    }

    fun updateChatMessage(chatMessage: ChatMessage) {
        this.chatMessage.add(chatMessage)
        notifyDataSetChanged()
    }

    fun updateUserId(userId: Int) {
        this.currentUserId = userId
    }
}