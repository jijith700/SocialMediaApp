package com.sma.liveler.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutPostItemBinding
import com.sma.liveler.interfaces.OnClickPostListener
import com.sma.liveler.utils.TYPE_IMAGE
import com.sma.liveler.utils.TYPE_TEXT
import com.sma.liveler.utils.TYPE_VIDEO
import com.sma.liveler.vo.Post
import de.hdodenhof.circleimageview.CircleImageView


class TimelineAdapter(private var onClickPostListener: OnClickPostListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val POST = 0
    private val FEED = 1
    private lateinit var context: Context
    private var posts: List<Post> = ArrayList<Post>()

    private lateinit var layoutPostItemBinding: LayoutPostItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? = null
        var viewHolder: RecyclerView.ViewHolder? = null

        if (viewType == POST) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_post_item, parent, false)
            viewHolder = PostViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_feed_item, parent, false)
            viewHolder = FeedViewHolder(view)
        }
        context = view.context

        return viewHolder
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position >= 1) {
            val feedViewHolder = holder as FeedViewHolder

            if (posts[position].type == TYPE_TEXT) {
                feedViewHolder.tvFeed?.text = posts[position].post
                feedViewHolder.tvFeed?.visibility = View.VISIBLE

            } else if (posts[position].type == TYPE_IMAGE) {
                feedViewHolder.ivFeed?.visibility = View.VISIBLE
                Glide.with(context).load(posts[position].image)
                    .into(feedViewHolder.ivFeed!!)

            } else if (posts[position].type == TYPE_VIDEO) {
                feedViewHolder.ivFeed?.visibility = View.VISIBLE
                Glide.with(context).load(posts[position].thumbnail).into(feedViewHolder.ivFeed!!)
                feedViewHolder.rlPlayButton?.visibility = View.VISIBLE
            }

            Glide.with(context).load(posts[position].image)
                .placeholder(R.drawable.ic_user_avtar)
                .into(feedViewHolder.ivUser!!)

            feedViewHolder.tvFeedTitle?.text = posts[position].userName
            feedViewHolder.tvTime?.text = "" + posts[position].postTime
            feedViewHolder.tvTotalComment?.text = "" + posts[position].commentsCount
            feedViewHolder.tvLikeCount?.text = "" + posts[position].likesCount

            if (posts[position].likesCount > 0) {
                feedViewHolder.tvLikeUsers?.visibility = View.GONE
                feedViewHolder.clUsers?.visibility = View.GONE
            } else {
                feedViewHolder.tvLikeUsers?.visibility = View.VISIBLE
                feedViewHolder.clUsers?.visibility = View.VISIBLE
            }

            feedViewHolder.ivLike?.setOnClickListener { onClickPostListener.onClickLike(posts[position].id) }
        } else {
            val postViewHolder = holder as PostViewHolder

            postViewHolder.btnPost?.setOnClickListener {
                val status = postViewHolder.edtStatus?.text.toString()
                if (!TextUtils.isEmpty(status)) {
                    onClickPostListener.onClickPost(status, TYPE_TEXT)
                    postViewHolder.edtStatus?.setText("")
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            POST
        else
            FEED
    }

    class PostViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        var edtStatus: EditText?
        var ivTypeLocation: ImageView?
        var ivTypeImage: ImageView?
        var ivTypeVideo: ImageView?
        var btnPost: Button?
        var btnPreview: Button?

        init {
            edtStatus = view?.findViewById(R.id.edtMessage)
            ivTypeImage = view?.findViewById(R.id.ivImage)
            ivTypeVideo = view?.findViewById(R.id.ivVideo)
            ivTypeLocation = view?.findViewById(R.id.ivLocation)
            btnPost = view?.findViewById(R.id.btnPost)
            btnPreview = view?.findViewById(R.id.btnPreview)
        }
    }

    class FeedViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var ivUser: CircleImageView?
        var tvFeedTitle: TextView?
        var tvFeed: TextView?
        var ivFeed: ImageView?
        var rlPlayButton: RelativeLayout?
        var tvTotalComment: TextView?
        var tvLikeCount: TextView?
        var tvLikeUsers: TextView?
        var tvTime: TextView?
        var clUsers: ConstraintLayout?
        var ivLike: ImageView?

        init {
            ivUser = view?.findViewById(R.id.ivUser)
            tvFeedTitle = view?.findViewById(R.id.tvFeedTitle)
            tvFeed = view?.findViewById(R.id.tvFeed)
            ivFeed = view?.findViewById(R.id.ivFeed)
            rlPlayButton = view?.findViewById(R.id.rlPlayButton)
            tvTotalComment = view?.findViewById(R.id.tvTotalComment)
            tvLikeCount = view?.findViewById(R.id.tvLikeCount)
            tvLikeUsers = view?.findViewById(R.id.tvLikeUser)
            tvTime = view?.findViewById(R.id.tvTime)
            clUsers = view?.findViewById(R.id.clUsers)
            ivLike = view?.findViewById(R.id.ivLike)

        }
    }

    fun updatePosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }
}