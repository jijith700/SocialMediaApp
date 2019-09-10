package com.sma.liveler.ui.registration

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.R
import com.sma.liveler.repository.UserRepository
import com.sma.liveler.ui.login.LoginActivity
import com.sma.liveler.vo.User
import timber.log.Timber

class RegisterViewModel() : ViewModel() {

    private lateinit var context: Context
    private lateinit var userRepository: UserRepository

    var user: MutableLiveData<User> = MutableLiveData()//= User("","","","","","","")
    var loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var success: MutableLiveData<Boolean> = MutableLiveData()

    val errorFirstName: MutableLiveData<String> = MutableLiveData()
    val errorMiddleName: MutableLiveData<String> = MutableLiveData()
    val errorLastName: MutableLiveData<String> = MutableLiveData()
    val errorEmail: MutableLiveData<String> = MutableLiveData()
    val errorPhone: MutableLiveData<String> = MutableLiveData()
    val errorPassword: MutableLiveData<String> = MutableLiveData()

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

    val loginClickListener = View.OnClickListener {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(context, intent, null)
        finishAffinity(context as RegisterActivity)
    }

    val registerClickListener = View.OnClickListener {
        val intent = Intent()
        Timber.d("register click")

    }

    fun register(user: User) {
        if (TextUtils.isEmpty(user.firstName)) {
            errorFirstName.value = context.getString(R.string.error_first_name)
        } else if (TextUtils.isEmpty(user.middleName)) {
            errorMiddleName.value = context.getString(R.string.error_middle_name)
        } else if (TextUtils.isEmpty(user.LastName)) {
            errorLastName.value = context.getString(R.string.error_last_name)
        } else if (TextUtils.isEmpty(user.phone)) {
            errorPhone.value = context.getString(R.string.error_phone)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            errorEmail.value = context.getString(R.string.error_email)
        } else if (TextUtils.isEmpty(user.password)) {
            errorPassword.value = context.getString(R.string.error_password)
        } else {
            userRepository.register(user)
        }
    }
}