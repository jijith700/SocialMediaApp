package com.sma.liveler.ui.timeline


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentTimeLineBinding
import com.sma.liveler.interfaces.OnClickMediaListener
import com.sma.liveler.interfaces.OnClickPostListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.TimelineAdapter
import com.sma.liveler.utils.*
import kotlinx.android.synthetic.main.fragment_my_video.pbLoading
import kotlinx.android.synthetic.main.fragment_time_line.*
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException


/**
 * A simple [Fragment] subclass.
 *
 */
class PostFragment : Fragment(), OnClickPostListener, OnClickMediaListener,
    RequestBodyProgress.UploadCallbacks {

    companion object {
        const val REQUEST_PICK_VIDEO = 83
        const val REQUEST_VIDEO_CAPTURE = 84
        const val REQUEST_PICK_IMAGE = 85
        const val REQUEST_PERMISSION = 100
    }

    private lateinit var binding: FragmentTimeLineBinding
    private lateinit var timelineAdapter: TimelineAdapter

    private var fileName: String = ""
    private var fileType = REQUEST_PICK_IMAGE


    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: TimelineViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return TimelineViewModel(
                    activity!!,
                    PostRepository(activity!!)
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_line, container, false)
        timelineAdapter = TimelineAdapter(this, this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTimeline.layoutManager = LinearLayoutManager(context)
        binding.rvTimeline.addItemDecoration(VerticalDividerItemDecoration(20, false))
        binding.rvTimeline.adapter = timelineAdapter

        viewModel.posts.observe(this, Observer {
            layoutLoading?.visibility = View.GONE
            timelineAdapter.updatePosts(it)
        })

        viewModel.getPosts()
    }

    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onClickLike(postId: Int) {
        viewModel.likePosts(postId, Utils.loadPreferenceInt(activity!!, USER_ID))
    }

    override fun onClickPost(status: String, type: String) {
        viewModel.addNewPost(status)
    }

    override fun onClickPost(status: String, type: String, file: File) {
        uploadFile(status, type, file)
    }

    override fun onClickImage() {
        fileType = REQUEST_PICK_IMAGE
        onSelectGallery()
    }

    override fun onClickVideo() {
        fileType = REQUEST_PICK_VIDEO
        onSelectGallery()
    }

    override fun onClickPreview(status: String, fileName: File, type: String) {
        if (type == TYPE_IMAGE) {
            val imagePreviewDialogFragment = ImagePreviewDialogFragment()
            imagePreviewDialogFragment.setStatus(status)
            imagePreviewDialogFragment.setFileName(fileName)
            imagePreviewDialogFragment.show(
                childFragmentManager,
                imagePreviewDialogFragment.javaClass.simpleName
            )
        } else if (type == TYPE_VIDEO) {
            val videoPreviewDialogFragment = VideoPreviewDialogFragment()
            videoPreviewDialogFragment.setStatus(status)
            videoPreviewDialogFragment.setFileName(fileName)
            videoPreviewDialogFragment.show(
                childFragmentManager,
                videoPreviewDialogFragment.javaClass.simpleName
            )
        } else {
            Timber.e("Invalid media")
        }
    }


    private fun onSelectGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    activity!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Storage permission has not been granted.
                requestPermission(REQUEST_PERMISSION)
            } else {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                if (fileType == REQUEST_PICK_IMAGE)
                    intent.type = "image/*"
                else
                    intent.type = "video/*"
                startActivityForResult(intent, fileType)
            }
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            if (fileType == REQUEST_PICK_IMAGE)
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
        if (requestCode == REQUEST_PERMISSION) {
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
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
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

                timelineAdapter.setFileName(filePath!!)

            } catch (e: NullPointerException) {
                fileName = "";
                Toast.makeText(activity!!, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Timber.e(e.toString())
            }

        }

        if (requestCode == REQUEST_PICK_VIDEO && resultCode == Activity.RESULT_OK) { //Image Upload

            val imageUri = data?.data
            try {
                Timber.e("FILE PATH %s", imageUri!!.path)
                Timber.e("FILE NAME %s", File(imageUri.path).name)

                val filePath = FileUtils.getFile(activity!!, imageUri)
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
                timelineAdapter.setFileName(filePath!!)
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

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
            activity!!.startActivityForResult(takePictureIntent, REQUEST_VIDEO_CAPTURE)
        }
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
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

    private fun uploadFile(status: String, type: String, filePath: File) {
        val fileBody = RequestBodyProgress(filePath, type, this)
        val body = MultipartBody.Part.createFormData("file", filePath.getName(), fileBody)
        viewModel.uploadMedia(status, type, body)
    }

    override fun onPause() {
        super.onPause()
        if (timelineAdapter != null) {
            timelineAdapter.stopAllPlayer()
        } else {
            Timber.e("timeline adapter is null")
        }
    }
}
