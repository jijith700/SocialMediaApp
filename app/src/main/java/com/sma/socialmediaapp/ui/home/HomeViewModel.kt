package com.sma.socialmediaapp.ui.home

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sma.socialmediaapp.base.BaseViewModel
import com.sma.socialmediaapp.network.WebApiListener
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class HomeViewModel() : BaseViewModel() {
    @Inject
    lateinit var webApiListener: WebApiListener
//    val postListAdapter: PostListAdapter = PostListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener {
        //loadPosts()
    }

    val loginClickListener = View.OnClickListener {

    }

    val signUpClickListener = View.OnClickListener {
        val intent = Intent()
    }

    private lateinit var subscription: Disposable

    private fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }
}