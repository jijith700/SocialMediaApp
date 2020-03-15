package com.sma.liveler.ui.timeline


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sma.liveler.R
import kotlinx.android.synthetic.main.fragment_video_preview_dialog.*
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class VideoPreviewDialogFragment : DialogFragment() {

    private var status = "";
    private lateinit var fileName: File
    private lateinit var player: SimpleExoPlayer


    override fun onResume() {
        super.onResume()
        val width = ConstraintLayout.LayoutParams.MATCH_PARENT
        val height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_preview_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        player = SimpleExoPlayer.Builder(context!!).build()

        playerView?.player = player

        // Produces DataSource instances through which media data is loaded.
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context!!,
            Util.getUserAgent(context!!, context!!.getString(R.string.app_name))
        )
        // This is the MediaSource representing the media to be played.
        // This is the MediaSource representing the media to be played.
        val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(fileName.toString()))
        // Prepare the player with the source.
        // Prepare the player with the source.

        player.prepare(videoSource)
        player.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        player.playWhenReady = true
        player.release()
    }

    fun setStatus(status: String) {
        this.status = status
    }

    fun setFileName(fileName: File) {
        this.fileName = fileName
    }

}
