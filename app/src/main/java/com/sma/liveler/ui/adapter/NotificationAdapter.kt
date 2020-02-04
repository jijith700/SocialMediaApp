package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutFriendItemBinding
import com.sma.liveler.vo.Friend
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class NotificationAdapter() : RecyclerView.Adapter<NotificationAdapter.FriendItemViewHolder>() {

    private lateinit var layoutFriendItemBinding: LayoutFriendItemBinding

    private var friends: List<Friend> = ArrayList<Friend>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendItemViewHolder {

        layoutFriendItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_notification_item,
                parent,
                false
            )

        context = layoutFriendItemBinding.root.context

        return FriendItemViewHolder(layoutFriendItemBinding.root)

    }

    override fun onBindViewHolder(holder: FriendItemViewHolder, position: Int) {
        /*Picasso.get().load(friends[position].cover_picture)
            .placeholder(R.drawable.ic_sample_video_thumbnail)
            .into(holder.ivCover!!)*/

        Picasso.get().load(friends[position].profile_picture)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder.ivUser!!)

        holder.tvUserName?.text = friends[position].name
        holder.tvDetails?.text = friends[position].email
    }

    override fun getItemCount(): Int {
        return friends.size;
    }

    class FriendItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        //        var ivCover : ImageView?
        var ivUser: CircleImageView?
        var tvUserName: TextView?
        var tvDetails: TextView?
        var btnRemove: Button?

        init {
//            ivCover = view?.findViewById(R.id.ivCover)
            ivUser = view?.findViewById(R.id.ivUser)
            tvUserName = view?.findViewById(R.id.tvUserName)
            tvDetails = view?.findViewById(R.id.tvUserDetails)
            btnRemove = view?.findViewById(R.id.btnRemove)
        }
    }

    fun updateFriends(friends: List<Friend>) {
        this.friends = friends
        notifyDataSetChanged()
    }
}