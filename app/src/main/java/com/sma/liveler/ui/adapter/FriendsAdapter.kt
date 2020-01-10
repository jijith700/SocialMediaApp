package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutFriendItemBinding
import com.sma.liveler.interfaces.OnClickFriendListener
import com.sma.liveler.vo.Friend
import de.hdodenhof.circleimageview.CircleImageView


class FriendsAdapter(private var onClickFriendListener: OnClickFriendListener) :
    RecyclerView.Adapter<FriendsAdapter.FriendItemViewHolder>() {

    private lateinit var layoutFriendItemBinding: LayoutFriendItemBinding

    private var friends: List<Friend> = ArrayList<Friend>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendItemViewHolder {

        layoutFriendItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_friend_item,
                parent,
                false
            )

        context = layoutFriendItemBinding.root.context

        return FriendItemViewHolder(layoutFriendItemBinding.root)

    }

    override fun onBindViewHolder(holder: FriendItemViewHolder, position: Int) {
        /*Glide.with(context).load(friends[position].cover_picture)
            .placeholder(R.drawable.ic_sample_video_thumbnail)
            .into(holder.ivCover!!)*/

        Glide.with(context).load(friends[position].profile_picture)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder.ivUser!!)

        holder.tvUserName?.text = friends[position].name
        holder.tvDetails?.text = friends[position].email
        holder.btnRemove?.setOnClickListener({
            onClickFriendListener.onRemove(friends[position].user_id)
        })
    }

    override fun getItemCount(): Int {
        return friends.size;
    }

    class FriendItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

//        var ivCover : ImageView?
        var ivUser : CircleImageView?
        var tvUserName : TextView?
        var tvDetails : TextView?
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