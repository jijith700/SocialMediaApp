package com.sma.liveler.ui.allusers

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.vo.AllUsers

class AllUsersViewModel() : ViewModel() {

    var TAG = AllUsersViewModel::class.java.simpleName

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var allUsers = MutableLiveData<List<AllUsers>>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        allUsers = postRepository.allUsers
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getAllUsers() {
        postRepository.getAllUsers()
    }

    fun addFriend(userId: Int) {
        postRepository.addFriend(userId)
    }
}