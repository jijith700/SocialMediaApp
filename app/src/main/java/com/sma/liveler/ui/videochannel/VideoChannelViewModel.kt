package com.sma.liveler.ui.videochannel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.api.WebApiListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.vo.Post
import javax.inject.Inject

class VideoChannelViewModel() : ViewModel() {

    var TAG = VideoChannelViewModel::class.java.simpleName

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var posts = MutableLiveData<List<Post>>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        posts = postRepository.posts
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getPosts() {
        postRepository.getPost()
    }

    fun getVideoPost() {
        postRepository.getVideoPost()
    }

    fun likePost(postId: Int, userId: Int) {
        postRepository.likePost(postId, userId)
    }
}