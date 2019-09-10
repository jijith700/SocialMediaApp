package com.sma.liveler.ui.login

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.R
import com.sma.liveler.repository.UserRepository

class LoginViewModel() : ViewModel() {

    private lateinit var context: Context
    private lateinit var userRepository: UserRepository

    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    var userName: MutableLiveData<String> = MutableLiveData()
    var password: MutableLiveData<String> = MutableLiveData()

    constructor(
        context: Context,
        userRepository: UserRepository
    ) : this() {
        this.context = context
        this.userRepository = userRepository
        this.loadingVisibility = userRepository.loading
        this.errorMessage = userRepository.errrorMessage
        this.success = userRepository.success
    }


    fun onclickLogin(userName: String, password: String) {
        if (TextUtils.isEmpty(userName)) {
            this.userName.value = context.getString(R.string.error_name)
        } else if (TextUtils.isEmpty(password)) {
            this.password.value = context.getString(R.string.error_password)
        } else {
            userRepository.login(userName, password)
        }
    }
}