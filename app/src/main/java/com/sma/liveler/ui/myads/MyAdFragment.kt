package com.sma.liveler.ui.myads


import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sma.liveler.R
import com.sma.liveler.databinding.FragmentMyAdBinding
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.ui.adapter.AdsAdapter
import com.sma.liveler.utils.*
import com.sma.liveler.vo.Post
import com.sma.liveler.vo.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_my_ad.*
import kotlinx.android.synthetic.main.fragment_my_video.*
import kotlinx.android.synthetic.main.fragment_my_video.pbLoading
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream


/**
 * A simple [Fragment] subclass.
 *
 */
class MyAdFragment : Fragment(), RequestBodyProgress.UploadCallbacks {

    private val REQUEST_PICK_VIDEO = 83
    private val REQUEST_VIDEO_CAPTURE = 84
    private val REQUEST_PERMISSION = 100

    private lateinit var binding: FragmentMyAdBinding
    private lateinit var adAdapter: AdsAdapter

    private var fileName: String = ""

    /**
     * Initializing the view model fo the current activity.
     */
    private val viewModel: MyAdViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MyAdViewModel(activity!!, PostRepository(activity!!)) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_ad, container, false)
        adAdapter = AdsAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMyAd.layoutManager = LinearLayoutManager(context)
        /*binding.rvVideoChannel.addItemDecoration(VerticalDividerItemDecoration(20, false))*/
        binding.rvMyAd.adapter = adAdapter

        viewModel.ads.observe(this, Observer {
            pbLoading.visibility = View.GONE
            if (!it.isNullOrEmpty()) {
                rvMyAd.visibility = View.VISIBLE
                tvNoAds.visibility = View.GONE
            } else {
                rvMyAd.visibility = View.GONE
                tvNoAds.visibility = View.VISIBLE
            }
            adAdapter.updateAds(it)
        })

        updateAds()
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

            Picasso.get().load(post.thumbnail).into(ivThumbnail!!)

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

    private fun updateUserDetails(user: User) {
        Picasso.get().load(user?.profile?.profile_picture).into(ivUser!!)
        tvFeedTitle?.text = user.firstName
        tvCredits.setText("Credits: " + user?.profile?.wallet_amount)
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
                intent.type = "video/*"
                startActivityForResult(intent, REQUEST_PICK_VIDEO)
            }
        } else {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "video/*"
            startActivityForResult(intent, REQUEST_PICK_VIDEO)
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
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            val extras = data?.extras
            try {
                val bitmap = extras!!.get("data") as Bitmap

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                val imageUri = getImageUri(activity!!, bitmap)

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                //                File finalFile = new File(getRealPathFromURI(imageUri));

                Timber.e("FILE PATH" + imageUri.getPath())
                //                Timber.e("FILE NAME", finalFile.toString());

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

                val fileBody = RequestBodyProgress(filePath!!, TYPE_IMAGE, this)

                //                RequestBody tokenBody = RequestBody.create(okhttp3.MultipartBody.FORM, token);
                //                RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), new String(token));
                //                MultipartBody.Part body = MultipartBody.Part.createFormData("myFile", new File(imageUri.getPath()).getName(), RequestBody.create(MediaType.parse("image/*"), fileBody));
                val body = MultipartBody.Part.createFormData("file", filePath.getName(), fileBody)

//                productImage.setImageBitmap(bitmap)


//                Picasso
//                        .with(activity!!)
//                        .load(File(fileName))
//                        .placeholder(R.drawable.ic_ph_product)
//                        .into(productImage)

            } catch (e: NullPointerException) {
                //                if (mProgressDialog.isShowing())
                //                    mProgressDialog.dismiss();
                //                fileName = "";
                Toast.makeText(activity!!, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Timber.e(e.toString())
            }

        }

        if (requestCode == REQUEST_PICK_VIDEO && resultCode == Activity.RESULT_OK) { //Image Upload

            val imageUri = data?.data
            val imageStream: InputStream
            try {
                imageStream = activity!!.contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(imageStream)

                Timber.e("FILE PATH %s", imageUri!!.path)
                Timber.e("FILE NAME %s", File(imageUri.path).name)

                //                String token = Utils.LoadPreferences(getApplicationContext(), Constants.TOKEN);
                //                Timber.e("TOKEN", token);

                //                MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), fileBody);

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

                val fileBody = RequestBodyProgress(filePath!!, TYPE_VIDEO, this)

                //                RequestBody tokenBody = RequestBody.create(okhttp3.MultipartBody.FORM, token);

                //                RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), new String(token));
                //                MultipartBody.Part body = MultipartBody.Part.createFormData("myFile", new File(imageUri.getPath()).getName(), RequestBody.create(MediaType.parse("image/*"), fileBody));
                val body = MultipartBody.Part.createFormData("file", filePath.getName(), fileBody)

                viewModel.uploadVideo(body)


            } catch (e: FileNotFoundException) {
                //                if (mProgressDialog.isShowing())
                //                    mProgressDialog.dismiss();
                //                fileName = "";
                Toast.makeText(activity!!, getString(R.string.error_pic), Toast.LENGTH_SHORT).show()
                Timber.e(e.toString())
            } catch (e: NullPointerException) {
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

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    override fun onProgressUpdate(percentage: Int) {
        pbLoading.visibility = View.VISIBLE
        pbLoading.progress = percentage
        Timber.d("progress %d", percentage)
    }

    override fun onError() {
        pbLoading.visibility = View.GONE
        Utils.alert(activity!!, "Upload failed")
    }

    override fun onFinish() {
        pbLoading.visibility = View.GONE
        Utils.alert(activity!!, "File uploaded successfully")
    }

    fun updateAds() {
        viewModel.getAds()
    }
}
