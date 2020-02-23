package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutNotificationItemBinding
import com.sma.liveler.vo.UnreadNotification
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class NotificationAdapter() :
    RecyclerView.Adapter<NotificationAdapter.NotificationItemViewHolder>() {

    private lateinit var layoutNotificationItemBinding: LayoutNotificationItemBinding

    private var notification: List<UnreadNotification> = ArrayList<UnreadNotification>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {

        layoutNotificationItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_notification_item,
                parent,
                false
            )

        context = layoutNotificationItemBinding.root.context

        return NotificationItemViewHolder(layoutNotificationItemBinding.root)
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {

        Picasso.get().load(notification[position].data.image)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder.ivUser!!)

        holder.tvNotification?.text = notification[position].data.message
        holder.tvTime?.text = notification[position].data.time
    }

    override fun getItemCount(): Int {
        return notification.size;
    }

    class NotificationItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var ivUser: CircleImageView?
        var tvNotification: TextView?
        var tvTime: TextView?

        init {
            ivUser = view?.findViewById(R.id.ivUser)
            tvNotification = view?.findViewById(R.id.tvNotification)
            tvTime = view?.findViewById(R.id.tvTime)
        }
    }

    fun updateNotification(notification: List<UnreadNotification>) {
        this.notification = notification
        notifyDataSetChanged()
    }
}