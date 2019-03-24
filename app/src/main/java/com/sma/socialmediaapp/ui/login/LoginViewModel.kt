package com.sma.socialmediaapp.ui.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sma.socialmediaapp.base.BaseViewModel
import com.sma.socialmediaapp.model.User
import com.sma.socialmediaapp.network.WebApiListener
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginViewModel() : BaseViewModel() {

    var TAG = LoginViewModel::class.java.simpleName

    @Inject
    lateinit var webApiListener: WebApiListener

    val userName: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    fun onclickLogin() {
//        val user  = User(userName.value, password.value)
//        Log.e(TAG, user.toString())
    }


}