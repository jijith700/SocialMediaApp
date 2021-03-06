package com.sma.liveler.ui.myvideos

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.api.WebApiListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.utils.TYPE_VIDEO
import com.sma.liveler.vo.Post
import com.sma.liveler.vo.User
import okhttp3.MultipartBody
import javax.inject.Inject

class MyVideoViewModel() : ViewModel() {

    var TAG = MyVideoViewModel::class.java.simpleName

    @Inject
    lateinit var webApiListener: WebApiListener

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var todaysPost = MutableLiveData<Post>()
    var posts = MutableLiveData<List<Post>>()
    var user = MutableLiveData<User>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        todaysPost = postRepository.todaysPost
        posts = postRepository.posts
        user = postRepository.user
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getDailyVideo() {
        postRepository.getDailyVideo()
    }

    fun uploadVideo(title: String, body: MultipartBody.Part) {
        postRepository.uploadMedia(title, true, TYPE_VIDEO, body)
    }

    fun redeem() {

    }
}