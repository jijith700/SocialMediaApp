package com.sma.socialmediaapp.ui.login

import androidx.lifecycle.MutableLiveData
import com.sma.socialmediaapp.base.BaseViewModel
import com.sma.socialmediaapp.model.User
import com.sma.socialmediaapp.network.WebApiListener
import com.sma.socialmediaapp.utils.AppLog
import javax.inject.Inject

class LoginViewModel() : BaseViewModel() {

    var TAG = LoginViewModel::class.java.simpleName

    @Inject
    lateinit var webApiListener: WebApiListener

    val userName: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()
    val loading: MutableLiveData<Int> = MutableLiveData()


    fun onclickLogin(userName: String, password: String) {
        val user = User("", userName, password)
        AppLog.e(TAG, user.toString())
        /*loading.value = View.VISIBLE
        webApiListener.login(user).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<LoginResponse>>() {
                override fun onComplete() {
                    Log.e(TAG, "Complete")
                }

                override fun onNext(t: Response<LoginResponse>) {
                    AppLog.e(TAG, "" + t.code())
                    if (t.code() == 200) {
                        AppLog.e(TAG, "success: " + t.body().toString())
                        error.value = t.body()!!.error
                    } else {
                        AppLog.e(TAG, "failed")

                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    AppLog.e(TAG, e.toString())
                    loading.value = View.GONE
                }
            });*/

        error.value = false
    }
}