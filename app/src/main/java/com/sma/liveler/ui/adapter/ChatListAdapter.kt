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
import com.sma.liveler.databinding.LayoutChatListItemBinding
import com.sma.liveler.interfaces.OnClickFriendListener
import com.sma.liveler.vo.Friend
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class ChatListAdapter(private var onClickFriendListener: OnClickFriendListener) :
    RecyclerView.Adapter<ChatListAdapter.FriendItemViewHolder>() {

    private lateinit var layoutChatListBinding: LayoutChatListItemBinding

    private var friends: List<Friend> = ArrayList<Friend>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendItemViewHolder {

        layoutChatListBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_chat_list_item,
                parent,
                false
            )

        context = layoutChatListBinding.root.context

        return FriendItemViewHolder(layoutChatListBinding.root)

    }

    override fun onBindViewHolder(holder: FriendItemViewHolder, position: Int) {
        Picasso.get().load(friends[position].profile_picture)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder.ivUser!!)

        holder.tvUserName?.text = friends[position].name
        holder.tvDetails?.text = friends[position].email
        holder.itemView.setOnClickListener({
            onClickFriendListener.onClick(friends[position])
        })
    }

    override fun getItemCount(): Int {
        return friends.size;
    }

    class FriendItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        var ivUser: CircleImageView?
        var tvUserName: TextView?
        var tvDetails: TextView?
        var btnRemove: Button?

        init {
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