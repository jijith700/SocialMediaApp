package com.sma.liveler.ui.adapter

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sma.liveler.R
import com.sma.liveler.databinding.LayoutPostItemBinding
import com.sma.liveler.interfaces.OnClickMediaListener
import com.sma.liveler.interfaces.OnClickPostListener
import com.sma.liveler.utils.TYPE_IMAGE
import com.sma.liveler.utils.TYPE_TEXT
import com.sma.liveler.utils.TYPE_VIDEO
import com.sma.liveler.vo.Post
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber
import java.io.File


class TimelineAdapter(
    private var onClickPostListener: OnClickPostListener,
    private var onClickMediaListener: OnClickMediaListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val POST = 0
    private val FEED = 1
    private lateinit var context: Context
    private var posts: List<Post> = ArrayList<Post>()
    private var postType = TYPE_TEXT;
    private var file: File? = null
    private var playerMap = HashMap<Int, SimpleExoPlayer>()

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
            val dataPosition = position - 1
            val feedViewHolder = holder as FeedViewHolder

            if (!TextUtils.isEmpty(posts[dataPosition].post)) {
                feedViewHolder.tvFeed?.text = posts[dataPosition].post
                feedViewHolder.tvFeed?.visibility = View.VISIBLE
            } else {
                feedViewHolder.tvFeed?.visibility = View.GONE
            }

            if (posts[dataPosition].type == TYPE_TEXT) {
                feedViewHolder.tvFeed?.text = posts[dataPosition].post
                feedViewHolder.tvFeed?.visibility = View.VISIBLE

            } else if (posts[dataPosition].type == TYPE_IMAGE) {
                feedViewHolder.ivFeed?.visibility = View.VISIBLE
                Picasso.get().load(posts[dataPosition].image)
                    .into(feedViewHolder.ivFeed!!)
            } else if (posts[dataPosition].type == TYPE_VIDEO) {
                feedViewHolder.ivFeed?.visibility = View.VISIBLE
                Picasso.get().load(posts[dataPosition].thumbnail)
                    .into(feedViewHolder.ivFeed!!)
                feedViewHolder.rlPlayButton?.visibility = View.VISIBLE


                val player = SimpleExoPlayer.Builder(context).build()

                feedViewHolder.playerView?.player = player

                playerMap.put(dataPosition, player)

                // Produces DataSource instances through which media data is loaded.
                // Produces DataSource instances through which media data is loaded.
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.getString(R.string.app_name))
                )
                // This is the MediaSource representing the media to be played.
                // This is the MediaSource representing the media to be played.
                val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(posts[dataPosition].video))
                // Prepare the player with the source.
                // Prepare the player with the source.

                feedViewHolder.ibPlay?.setOnClickListener {
                    stopAllPlayer()
                    player.prepare(videoSource)
                    player.playWhenReady = true
                    feedViewHolder.pbLoading?.visibility = View.VISIBLE
                    feedViewHolder.ibPlay?.visibility = View.GONE
                }

                /*           feedViewHolder.playerPlay?.setOnClickListener {
                               stopAllPlayer()
                               player.playWhenReady = true
                               feedViewHolder.pbLoading?.visibility = View.GONE
                               feedViewHolder.ibPlay?.visibility = View.GONE
                           }

                           feedViewHolder.playerPause?.setOnClickListener {
                               feedViewHolder.pbLoading?.visibility = View.GONE
                               feedViewHolder.ibPlay?.visibility = View.VISIBLE
                           }*/



                player.addListener(object : Player.EventListener {

                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        if (playbackState == Player.STATE_READY) {
                            feedViewHolder.ivFeed?.visibility = View.GONE
                            feedViewHolder.pbLoading?.visibility = View.GONE
                            feedViewHolder.ibPlay?.visibility = View.GONE
                            feedViewHolder.playerView?.visibility = View.VISIBLE
                        } else if (playbackState == Player.STATE_BUFFERING) {
                            feedViewHolder.pbLoading?.visibility = View.VISIBLE
                        } else if (playbackState == Player.STATE_ENDED) {
                            feedViewHolder.ibPlay?.visibility = View.VISIBLE
                        }
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {

                    }
                })
            }

            Picasso.get().load(posts[dataPosition].user.profile.profile_picture)
                .placeholder(R.drawable.ic_user_avtar)
                .into(feedViewHolder.ivUser!!)

            feedViewHolder.tvFeedTitle?.text = posts[dataPosition].userName
            feedViewHolder.tvTime?.text = "" + posts[dataPosition].postTime
            feedViewHolder.tvTotalComment?.text = "" + posts[dataPosition].commentsCount
            feedViewHolder.tvLikeCount?.text = "" + posts[dataPosition].likesCount

            if (posts[dataPosition].likesCount > 0) {
                feedViewHolder.tvLikeUsers?.visibility = View.GONE
                feedViewHolder.clUsers?.visibility = View.GONE
            } else {
                feedViewHolder.tvLikeUsers?.visibility = View.VISIBLE
                feedViewHolder.clUsers?.visibility = View.VISIBLE
            }

            feedViewHolder.ivLike?.setOnClickListener { onClickPostListener.onClickLike(posts[dataPosition].id) }

        } else {
            val postViewHolder = holder as PostViewHolder

            postViewHolder.btnPost?.setOnClickListener {
                val status = postViewHolder.edtStatus?.text.toString()
                if (!TextUtils.isEmpty(status) && postType.equals(TYPE_TEXT)) {
                    onClickPostListener.onClickPost(status, postType)
                    postViewHolder.edtStatus?.setText("")
                    postType = TYPE_TEXT
                    file = null
                } else if (!TextUtils.isEmpty(status)) {
                    onClickPostListener.onClickPost(status, postType, file!!)
                    postViewHolder.edtStatus?.setText("")
                    postType = TYPE_TEXT
                    file = null
                } else {
                    Toast.makeText(
                        context,
                        context.getText(R.string.error_status),
                        Toast.LENGTH_LONG
                    ).show()
                    Timber.w("Invalid post")
                }
            }

            postViewHolder.ivTypeImage?.setOnClickListener {
                postType = TYPE_IMAGE
                onClickMediaListener.onClickImage()
            }

            postViewHolder.ivTypeVideo?.setOnClickListener {
                postType = TYPE_VIDEO
                onClickMediaListener.onClickVideo()
            }

            postViewHolder.btnPreview?.setOnClickListener {
                val status = postViewHolder.edtStatus?.text.toString()
                if (!TextUtils.isEmpty(status)) {
                    onClickMediaListener.onClickPreview(status, file!!, postType)
                } else {
                    Toast.makeText(
                        context,
                        context.getText(R.string.error_status),
                        Toast.LENGTH_LONG
                    ).show()
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
        var ivTypeImage: ImageView?
        var ivTypeVideo: ImageView?
        var btnPost: Button?
        var btnPreview: Button?

        init {
            edtStatus = view?.findViewById(R.id.edtMessage)
            ivTypeImage = view?.findViewById(R.id.ivImage)
            ivTypeVideo = view?.findViewById(R.id.ivVideo)
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
        var playerView: PlayerView?
        var ibPlay: ImageButton?
        var pbLoading: ProgressBar?
        var playerPlay: ImageButton?
        var playerPause: ImageButton?


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
            playerView = view?.findViewById(R.id.playerView)
            ibPlay = view?.findViewById(R.id.ibPlay)
            pbLoading = view?.findViewById(R.id.pbLoading)
            playerPlay = view?.findViewById(R.id.exo_play)
            playerPause = view?.findViewById(R.id.exo_pause)
        }
    }

    fun updatePosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun setFileName(file: File) {
        this.file = file
    }

    fun stopAllPlayer() {
        for (i in 0..posts.size) {
            val player = playerMap[i]
            if (player != null) {
                player.playWhenReady = false
            }
        }
    }

    fun update() {
    }
}