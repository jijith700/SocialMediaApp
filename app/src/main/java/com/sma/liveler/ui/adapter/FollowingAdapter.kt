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
import com.sma.liveler.databinding.LayoutFriendRequestItemBinding
import com.sma.liveler.interfaces.OnClickFriendListener
import com.sma.liveler.vo.Request
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class FollowingAdapter(private var onClickFriendListener: OnClickFriendListener) :
    RecyclerView.Adapter<FollowingAdapter.FriendItemViewHolder>() {

    private lateinit var layoutFriendItemBinding: LayoutFriendRequestItemBinding

    private var friendsRequest: List<Request> = ArrayList<Request>()
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendItemViewHolder {

        layoutFriendItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_friend_request_item,
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

        Picasso.get().load(friendsRequest[position].fpp)
            .placeholder(R.drawable.ic_user_avtar)
            .into(holder.ivUser!!)

        holder.tvUserName?.text = friendsRequest[position].name
        holder.tvDetails?.text = friendsRequest[position].email

        holder.btnAccept?.setOnClickListener {
            onClickFriendListener.onAdd(friendsRequest[position].user_first_id)
        }

        holder.btnDecline?.setOnClickListener {
            onClickFriendListener.onRemove(friendsRequest[position].user_first_id)
        }
    }

    override fun getItemCount(): Int {
        return friendsRequest.size;
    }

    class FriendItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

//        var ivCover : ImageView?
        var ivUser : CircleImageView?
        var tvUserName : TextView?
        var tvDetails : TextView?
        var btnAccept: Button?
        var btnDecline: Button?

        init {
//            ivCover = view?.findViewById(R.id.ivCover)
            ivUser = view?.findViewById(R.id.ivUser)
            tvUserName = view?.findViewById(R.id.tvUserName)
            tvDetails = view?.findViewById(R.id.tvUserDetails)
            btnAccept = view?.findViewById(R.id.btnAccept)
            btnDecline = view?.findViewById(R.id.btnDecline)

        }
    }

    fun updateFollowing(friends: List<Request>) {
        this.friendsRequest = friends
        notifyDataSetChanged()
    }
}