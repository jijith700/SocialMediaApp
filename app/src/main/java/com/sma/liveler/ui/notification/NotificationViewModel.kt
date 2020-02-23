package com.sma.liveler.ui.notification

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.vo.Friend
import com.sma.liveler.vo.FriendRequest
import com.sma.liveler.vo.UnreadNotification

class NotificationViewModel() : ViewModel() {

    var TAG = NotificationViewModel::class.java.simpleName

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var friends = MutableLiveData<List<Friend>>()
    var notifications = MutableLiveData<List<UnreadNotification>>()
    var friendRequest = MutableLiveData<List<FriendRequest>>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        friends = postRepository.friends
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
        notifications = postRepository.notifications
        friendRequest = postRepository.friendRequest
    }

    fun getNotifications() {
        postRepository.getNotifications()
    }

    fun readAllNotifications() {
        postRepository.readAllNotifications()
    }
}