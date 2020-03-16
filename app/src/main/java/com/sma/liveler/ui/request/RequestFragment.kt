package com.sma.liveler.ui.request


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentRequestBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.timeline.PostFragment
import com.sma.liveler.utils.FileUtils
import com.sma.liveler.utils.RequestBodyProgress
import com.sma.liveler.utils.TYPE_VIDEO
import com.sma.liveler.utils.Utils
import kotlinx.android.synthetic.main.fragment_request.*
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException


/**
 * A simple [Fragment] subclass.
 *
 */
class RequestFragment : Fragment(), RequestBodyProgress.UploadCallbacks {

    companion object {
        const val REQUEST_PICK_VIDEO = 83
        const val REQUEST_VIDEO_CAPTURE = 84
        const val REQUEST_PICK_IMAGE = 85
        const val REQUEST_PERMISSION = 100
    }

    private lateinit var binding: FragmentRequestBinding

    private var fileName: String = ""
    private var filePath: File? = null
    private var fileType = REQUEST_PICK_IMAGE

    private var player: SimpleExoPlayer? = null

    private lateinit var onAdPostListener: OnAdPostListener

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: RequestViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return RequestViewModel(activity!!, PostRepository(activity!!)) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*binding.rvVideoChannel.addItemDecoration(VerticalDividerItemDecoration(20, false))*/

        btnBrowse?.setOnClickListener {
            fileType = PostFragment.REQUEST_PICK_VIDEO
            onSelectGallery()
        }

        btnRequestAd?.setOnClickListener {
            val title = edtTitle?.text.toString()
            if (!TextUtils.isEmpty(title)) {
                player?.playWhenReady = false
                uploadFile(title, TYPE_VIDEO, filePath!!)
            } else {
                Toast.makeText(
                    context,
                    getText(R.string.error_title),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.success.observe(this, Observer {
            layoutLoading?.visibility = View.GONE
            playerView.visibility = View.GONE
            ivThumbnail.visibility = View.VISIBLE
            edtTitle.setText("")
        })
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    private fun onSelectGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Storage permission has not been granted.
                requestPermission(PostFragment.REQUEST_PERMISSION)
            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                if (fileType == PostFragment.REQUEST_PICK_IMAGE)
                    intent.type = "image/*"
                else
                    intent.type = "video/*"
                startActivityForResult(intent, fileType)
            }
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            if (fileType == PostFragment.REQUEST_PICK_IMAGE)
                intent.type = "image/*"
            else
                intent.type = "video/*"
            startActivityForResult(intent, fileType)
        }
    }

    private fun requestPermission(id: Int) {
        Timber.d("Storage permission has NOT been granted. Requesting permission.")

        // Storage permission has not been granted yet. Request it directly.
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            id
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PostFragment.REQUEST_PERMISSION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onSelectGallery()
            } else {
                Toast.makeText(
                    activity!!,
                    "Please allow permission to continue.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PostFragment.REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            try {
                Timber.e("FILE PATH %s", imageUri!!.path)
                Timber.e("FILE NAME %s", File(imageUri.path).name)

                val filePath = FileUtils.getFile(activity!!, imageUri)
                Timber.e("NEW PATH %s", filePath.toString())

                fileName = filePath.toString()
                Timber.e(fileName.substring(fileName.lastIndexOf(".") + 1))

                if (!fileName.substring(fileName.lastIndexOf(".") + 1).equals(
                        "PNG",
                        ignoreCase = true
                    ) &&
                    !fileName.substring(fileName.lastIndexOf(".") + 1).equals(
                        "JPEG",
                        ignoreCase = true
                    ) &&
                    !fileName.substring(fileName.lastIndexOf(".") + 1).equals(
                        "JPG",
                        ignoreCase = true
                    )
                ) {
                    Toast.makeText(
                        activity!!,
                        getString(R.string.error_invalid_file),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

            } catch (e: NullPointerException) {
                fileName = "";
                Toast.makeText(activity!!, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Timber.e(e.toString())
            }

        }

        if (requestCode == PostFragment.REQUEST_PICK_VIDEO && resultCode == Activity.RESULT_OK) { //Image Upload

            val imageUri = data?.data
            try {
                Timber.e("FILE PATH %s", imageUri!!.path)
                Timber.e("FILE NAME %s", File(imageUri.path).name)

                this.filePath = FileUtils.getFile(activity!!, imageUri)
                Timber.e("NEW PATH %s", filePath.toString())

                fileName = filePath.toString()
                Timber.e(fileName.substring(fileName.lastIndexOf(".") + 1))

                if (!fileName.substring(fileName.lastIndexOf(".") + 1).equals(
                        "mp4",
                        ignoreCase = true
                    )
                ) {
                    Toast.makeText(
                        activity!!,
                        getString(R.string.error_invalid_file),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                player = SimpleExoPlayer.Builder(context!!).build()
                playerView?.player = player
                playerView?.visibility = View.VISIBLE
                ivThumbnail?.visibility = View.INVISIBLE

                // Produces DataSource instances through which media data is loaded.
                // Produces DataSource instances through which media data is loaded.
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context!!,
                    Util.getUserAgent(context!!, context!!.getString(R.string.app_name))
                )
                // This is the MediaSource representing the media to be played.
                // This is the MediaSource representing the media to be played.
                val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(fileName))
                // Prepare the player with the source.
                // Prepare the player with the source.

                player?.prepare(videoSource)
                player?.playWhenReady = true

            } catch (e: FileNotFoundException) {
                fileName = "";
                Toast.makeText(activity!!, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Timber.e(e.toString())
            } catch (e: NullPointerException) {
                fileName = "";
                Toast.makeText(activity!!, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Timber.e(e.toString())
            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        layoutLoading.visibility = View.VISIBLE
        pbLoading.progress = percentage
        Timber.d("progress %d", percentage)
    }

    override fun onError() {
        layoutLoading.visibility = View.GONE
        Utils.alert(activity!!, "Upload failed")
    }

    override fun onFinish() {
        layoutLoading.visibility = View.GONE
        Utils.alert(activity!!, "File uploaded successfully")
    }

    private fun uploadFile(title: String, type: String, filePath: File) {
        val fileBody = RequestBodyProgress(filePath, type, this)
        val body = MultipartBody.Part.createFormData("file", filePath.getName(), fileBody)
        viewModel.uploadMedia(title, body)
    }

    override fun onPause() {
        super.onPause()
        player?.playWhenReady = false
        player?.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.playWhenReady = false
        player?.release()
    }

    fun setOnAdPostListener(onAdPostListener: OnAdPostListener) {
        this.onAdPostListener = onAdPostListener
    }

    interface OnAdPostListener {
        fun onAdPost()
    }
}
