package com.sma.socialmediaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sma.socialmediaapp.R
import com.sma.socialmediaapp.databinding.LayoutPostItemBinding


class TimelineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val POST = 0
    private val FEED = 1

    private lateinit var layoutPostItemBinding: LayoutPostItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        var viewHolder: RecyclerView.ViewHolder? = null

        if (viewType == POST) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_post_item, parent, false)
            viewHolder = PostViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.layout_feed_item, parent, false)
            viewHolder = FeedViewHolder(view)
        }

        /* layoutPostItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_post_item, parent, false);

         return PostViewHolder(layoutPostItemBinding.root)*/

        return viewHolder
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            POST;
        else
            FEED;
    }


    class PostViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }

    class FeedViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }
}