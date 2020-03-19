package com.sma.liveler.ui.myvideos


import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_video_preview_dialog.playerView
import kotlinx.android.synthetic.main.fragment_video_upload_dialog.*
import kotlinx.android.synthetic.main.layout_post_item.btnPost
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class VideoUploadDialogFragment : DialogFragment() {

    private lateinit var fileName: File
    private lateinit var player: SimpleExoPlayer
    private lateinit var onClickPostListener: OnClickPostListener

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
        return inflater.inflate(R.layout.fragment_video_upload_dialog, container, false)
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

        btnPost.setOnClickListener {
            val title = edtTitle.text.toString()
            if (!TextUtils.isEmpty(title)) {
                onClickPostListener?.onClickPost(title, fileName)
                dismiss()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.error_title),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnCancel?.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.playWhenReady = true
        player.release()
    }

    fun setFileName(fileName: File) {
        this.fileName = fileName
    }

    fun setOnClickPostListener(onClickPostListener: OnClickPostListener) {
        this.onClickPostListener = onClickPostListener
    }

    interface OnClickPostListener {
        fun onClickPost(title: String, fileName: File)
    }

}
