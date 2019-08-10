package com.sma.socialmediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sma.socialmediaapp.R


class GroupAdapter : RecyclerView.Adapter<GroupAdapter.GroupItemViewHolder>() {

    private lateinit var layoutGroupItemBinding: com.sma.socialmediaapp.databinding.LayoutGroupItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupItemViewHolder {

        layoutGroupItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_group_item, parent, false);

        return GroupItemViewHolder(layoutGroupItemBinding.root)

    }

    override fun onBindViewHolder(holder: GroupItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class GroupItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }
}