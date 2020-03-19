package com.sma.liveler.ui.myads

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.api.WebApiListener
import com.sma.liveler.repository.PostRepository
import com.sma.liveler.utils.TYPE_VIDEO
import com.sma.liveler.vo.Ad
import com.sma.liveler.vo.User
import okhttp3.MultipartBody
import javax.inject.Inject

class MyAdViewModel() : ViewModel() {

    var TAG = MyAdViewModel::class.java.simpleName

    @Inject
    lateinit var webApiListener: WebApiListener

    private lateinit var context: Context
    private lateinit var postRepository: PostRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var ads = MutableLiveData<List<Ad>>()
    var user = MutableLiveData<User>()

    constructor(context: Context, postRepository: PostRepository) : this() {
        this.context = context
        this.postRepository = postRepository
        ads = postRepository.ads
        user = postRepository.user
        loadingVisibility = postRepository.loading
        errorMessage = postRepository.errrorMessage
        success = postRepository.success
    }

    fun getAds() {
        postRepository.getAds()
    }

    fun uploadVideo(body: MultipartBody.Part) {
        postRepository.uploadMedia("", false, TYPE_VIDEO, body)
    }
}