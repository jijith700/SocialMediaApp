package com.sma.liveler.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutPostItemBinding


class FavFeedAdapter : RecyclerView.Adapter<FavFeedAdapter.FeedViewHolder>() {

    private val POST = 0
    private val FEED = 1

    private lateinit var layoutPostItemBinding: LayoutPostItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavFeedAdapter.FeedViewHolder {
        var view: View? = null
        var viewHolder: RecyclerView.ViewHolder? = null

        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_favorite_item, parent, false)
        viewHolder = FeedViewHolder(view)

        /* layoutPostItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_post_item, parent, false);

         return PostViewHolder(layoutPostItemBinding.root)*/

        return viewHolder
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: FavFeedAdapter.FeedViewHolder, position: Int) {
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            POST;
        else
            FEED;
    }

    class FeedViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }
}