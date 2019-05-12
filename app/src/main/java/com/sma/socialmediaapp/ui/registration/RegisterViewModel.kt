package com.sma.socialmediaapp.ui.registration

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.sma.socialmediaapp.base.BaseViewModel
import com.sma.socialmediaapp.model.RegistrationResponse
import com.sma.socialmediaapp.model.User
import com.sma.socialmediaapp.network.WebApiListener
import com.sma.socialmediaapp.utils.AppLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class RegisterViewModel() : BaseViewModel() {

    var TAG = RegisterViewModel::class.java.simpleName

    @Inject
    lateinit var webApiListener: WebApiListener
//    val postListAdapter: PostListAdapter = PostListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val success: MutableLiveData<Boolean> = MutableLiveData()
    val loading: MutableLiveData<Int> = MutableLiveData()

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

    fun register(name: String, email: String, password: String) {
        val user = User(name, email, password)
        AppLog.e(TAG, user.toString())
        loading.value = View.VISIBLE
        webApiListener.register(user).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<RegistrationResponse>>() {
                override fun onComplete() {
                    Log.e(TAG, "Complete")
                }

                override fun onNext(t: Response<RegistrationResponse>) {
                    AppLog.e(TAG, "" + t.code())
                    if (t.code() == 200) {
                        AppLog.e(TAG, "success: " + t.body().toString())
                        success.value = t.body()!!.success
                    } else {
                        AppLog.e(TAG, "failed")

                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    AppLog.e(TAG, e.toString())
                    loading.value = View.GONE
                }
            });

    }
}