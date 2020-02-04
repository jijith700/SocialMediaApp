package com.sma.liveler.ui.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
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
import com.sma.liveler.databinding.LayoutVideoItemBinding
import com.sma.liveler.interfaces.OnClickPostListener
import com.sma.liveler.vo.Post
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class VideoChannelAdapter(private var onClickPostListener: OnClickPostListener)
    : RecyclerView.Adapter<VideoChannelAdapter.VideoItemViewHolder>() {

    private lateinit var layoutVideoItemBinding: LayoutVideoItemBinding

    private lateinit var context: Context
    private var videoPost: List<Post> = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemViewHolder {

        layoutVideoItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_video_item, parent, false);

        return VideoItemViewHolder(layoutVideoItemBinding.root)

    }

    override fun onBindViewHolder(holder: VideoItemViewHolder, position: Int) {
        holder.ivThumbnail?.visibility = View.VISIBLE
        Picasso.get().load(videoPost[position].thumbnail).into(holder.ivThumbnail!!)
        holder.rlPlayButton?.visibility = View.VISIBLE

        holder.tvFeedTitle?.text = videoPost[position].userName
        holder.tvTime?.text = "" + videoPost[position].postTime
        holder.tvLikeCount?.text = "" + videoPost[position].likesCount

        holder.ivLike?.setOnClickListener { onClickPostListener.onClickLike(videoPost[position].id) }

        val player = SimpleExoPlayer.Builder(context).build()

        holder.playerView?.player = player

        // Produces DataSource instances through which media data is loaded.
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.getString(R.string.app_name))
        )
        // This is the MediaSource representing the media to be played.
        // This is the MediaSource representing the media to be played.
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(videoPost[position].video))
        // Prepare the player with the source.
        // Prepare the player with the source.

        holder.ibPlay?.setOnClickListener {
            player.prepare(videoSource)
            player.playWhenReady = true
            holder.pbLoading?.visibility = View.VISIBLE
            holder.ibPlay?.visibility = View.GONE
        }

        player.addListener(object : Player.EventListener {

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    holder.ivThumbnail?.visibility = View.GONE
                    holder.pbLoading?.visibility = View.GONE
                    holder.ibPlay?.visibility = View.GONE
                    holder.playerView?.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_BUFFERING) {
                    holder.pbLoading?.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_ENDED) {
                    holder.ibPlay?.visibility = View.VISIBLE
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {

            }
        })
    }

    override fun getItemCount(): Int {
        return videoPost.size
    }

    class VideoItemViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        var ivUser: CircleImageView?
        var tvFeedTitle: TextView?
        var ivThumbnail: ImageView?
        //        var tvFeed: TextView?
        var rlPlayButton: RelativeLayout?
        var tvLikeCount: TextView?
        var tvTime: TextView?
        var ivLike: ImageView?
        var playerView: PlayerView?
        var ibPlay: ImageButton?
        var pbLoading: ProgressBar?

        init {
            ivUser = view?.findViewById(R.id.ivUser)
            tvFeedTitle = view?.findViewById(R.id.tvFeedTitle)
            ivThumbnail = view?.findViewById(R.id.ivThumbnail)
//            tvFeed = view?.findViewById(R.id.tvFeed)
            rlPlayButton = view?.findViewById(R.id.rlPlayButton)
            tvLikeCount = view?.findViewById(R.id.tvTotalLike)
            tvTime = view?.findViewById(R.id.tvTime)
            ivLike = view?.findViewById(R.id.ivLike)
            playerView = view?.findViewById(R.id.playerView)
            ibPlay = view?.findViewById(R.id.ibPlay)
            pbLoading = view?.findViewById(R.id.pbLoading)
        }
    }

    fun updatePosts(videoPost: List<Post>) {
        this.videoPost = videoPost
        notifyDataSetChanged()
    }
}