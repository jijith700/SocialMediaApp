package com.sma.liveler.ui.following

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.api.WebApiListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.vo.Friend
import com.sma.liveler.vo.Post
import javax.inject.Inject

class FollowingViewModel() : ViewModel() {

    var TAG = FollowingViewModel::class.java.simpleName

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var friends = MutableLiveData<List<Friend>>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        friends = postRepository.friends
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getFollowing() {
        postRepository.getFriends(0)
    }
}