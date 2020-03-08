package com.sma.liveler.ui.chat

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.vo.ChatMessage
import com.sma.liveler.vo.Friend
import com.sma.liveler.vo.User

class ChatViewModel() : ViewModel() {

    var TAG = ChatViewModel::class.java.simpleName

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var friends = MutableLiveData<List<Friend>>()
    var sendUser = MutableLiveData<User>()
    var receiverUser = MutableLiveData<User>()
    var chatMessages = MutableLiveData<List<ChatMessage>>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        friends = postRepository.friends
        sendUser = postRepository.sendUser
        receiverUser = postRepository.receiverUser
        chatMessages = postRepository.chatMessages
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getMessage(userId: Int) {
        postRepository.getMessage(userId)
    }

    fun sendMessage(fromUser: Int, toUser: Int, msg: String) {
        postRepository.sendMessage(fromUser, toUser, msg)
    }
}