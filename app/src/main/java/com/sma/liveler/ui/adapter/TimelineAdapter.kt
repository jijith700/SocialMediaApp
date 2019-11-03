package com.sma.liveler.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutPostItemBinding
import com.sma.liveler.vo.Post


class TimelineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private var posts: List<Post> = ArrayList<Post>()


    private lateinit var layoutPostItemBinding: LayoutPostItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        var viewHolder: RecyclerView.ViewHolder? = null

        view = LayoutInflater.from(parent.context).inflate(R.layout.layout_feed_item, parent, false)
        viewHolder = FeedViewHolder(view)

        context = view.context
        /* layoutPostItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_post_item, parent, false);

         return PostViewHolder(layoutPostItemBinding.root)*/

        return viewHolder
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        Glide.with(context).load(posts[position].post)
    }

    /*override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            POST
        else
            FEED
    }*/


    class PostViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        init {

        }

    }

    class FeedViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var ivUser: ImageView?
        var tvFeedTitle: TextView?
        init {
            ivUser = view?.findViewById(R.id.ivUser)
            tvFeedTitle = view?.findViewById(R.id.tvFeedTitle)
        }

    }

    fun updatePosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }
}