package com.sma.liveler.ui.following

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.vo.Request

class FollowingViewModel() : ViewModel() {

    var TAG = FollowingViewModel::class.java.simpleName

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var friendsRequest = MutableLiveData<List<Request>>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        friendsRequest = postRepository.friendsRequest
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getFollowing() {
        postRepository.getFollowing()
    }

    fun acceptFriendRequest(userId: Int) {
        postRepository.acceptFriendRequest(userId)
    }

    fun cancelFriendRequest(userId: Int) {
        postRepository.cancelFriendRequest(userId)
    }
}