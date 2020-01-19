package com.sma.liveler.ui.myvideos


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentMyVideoBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.MyVideoAdapter
import com.sma.liveler.vo.Post
import com.sma.liveler.vo.User
import kotlinx.android.synthetic.main.fragment_my_video.*


/**
 * A simple [Fragment] subclass.
 *
 */
class MyVideoFragment : Fragment() {

    private lateinit var binding: FragmentMyVideoBinding
    private lateinit var myVideoAdapter: MyVideoAdapter

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: MyVideoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MyVideoViewModel(activity!!, PostRepository(activity!!)) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_video, container, false)
        myVideoAdapter = MyVideoAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clTodaysVideoParent.visibility = View.GONE

        viewModel.todaysPost.observe(this, Observer {
            updateVideoDetails(it)
        })

        viewModel.user.observe(this, Observer {
            updateUserDetails(it)
        })

        viewModel.getDailyVideo()
        pbLoading.visibility = View.VISIBLE
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    fun updateVideoDetails(post: Post) {
        if (post != null) {
            clTodaysVideoParent.visibility = View.VISIBLE
            tvNoVideo.visibility = View.GONE
            pbLoading.visibility = View.GONE
            ivThumbnail?.visibility = View.VISIBLE
            rlPlayButton?.visibility = View.VISIBLE

            Glide.with(this).load(post.thumbnail).into(ivThumbnail!!)

            tvFeedTitle?.text = post.userName
            tvTime?.text = "" + post.postTime
            tvTotalLike?.text = "" + post.likesCount
            tvRemainingTime.text = "Time Remaining: " + post.time_remaining


            val player = SimpleExoPlayer.Builder(activity!!).build()

            playerView?.player = player

            // Produces DataSource instances through which media data is loaded.
            // Produces DataSource instances through which media data is loaded.
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                activity!!,
                Util.getUserAgent(activity!!, getString(R.string.app_name))
            )
            // This is the MediaSource representing the media to be played.
            // This is the MediaSource representing the media to be played.
            val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(post.video))
            // Prepare the player with the source.
            // Prepare the player with the source.

            ibPlay?.setOnClickListener {
                player.prepare(videoSource)
                player.playWhenReady = true
                pbLoading?.visibility = View.VISIBLE
                ibPlay?.visibility = View.GONE
            }

            player.addListener(object : Player.EventListener {

                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        ivThumbnail?.visibility = View.GONE
                        pbLoading?.visibility = View.GONE
                        ibPlay?.visibility = View.GONE
                        playerView?.visibility = View.VISIBLE
                    } else if (playbackState == Player.STATE_BUFFERING) {
                        pbLoading?.visibility = View.VISIBLE
                    } else if (playbackState == Player.STATE_ENDED) {
                        ibPlay?.visibility = View.VISIBLE
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {

                }
            })
        } else {
            clTodaysVideoParent.visibility = View.GONE
            pbLoading.visibility = View.GONE
            tvNoVideo.visibility = View.VISIBLE
        }
    }

    fun updateUserDetails(user: User) {
        Glide.with(this).load(user?.profile?.profile_picture).into(ivUser!!)
        tvFeedTitle?.text = user.firstName
        tvCredits.setText("Credits: " + user?.profile?.wallet_amount)
    }
}
