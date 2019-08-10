package com.sma.socialmediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.socialmediaapp.R


class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.FriendItemViewHolder>() {

    private lateinit var layoutFriendItemBinding: LayoutFriendItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendItemViewHolder {

        layoutFriendItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_friend_item, parent, false);

        return FriendItemViewHolder(layoutFriendItemBinding.root)

    }

    override fun onBindViewHolder(holder: FriendItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class FriendItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }
}