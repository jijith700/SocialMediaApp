package com.sma.liveler.repository

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sma.liveler.R
import com.sma.liveler.api.ApiService
import com.sma.liveler.utils.*
import com.sma.liveler.vo.LoginResponse
import com.sma.liveler.vo.RegistrationResponse
import com.sma.liveler.vo.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber


class UserRepository(var context: Context) {

    var loading = MutableLiveData<Int>()
    var success = MutableLiveData<Boolean>()
    var errrorMessage = MutableLiveData<String>()

    /**
     * Variable hold the object of ApiService and the it will initialized here.
     */
    private val apiService by lazy {
        ApiService.create()
    }

    /**
     * Method to register user.
     */
    fun register(user: User?) {
        Timber.e(user.toString())
        loading.value = View.VISIBLE

        val request = JSONObject()
        try {
            request.accumulate(NAME, user?.firstName)
            request.accumulate(EMAIL, user?.email)
            request.accumulate(PASSWORD, user?.password)
            request.accumulate(C_PASSWORD, user?.password)
            Timber.d(request.toString());
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        Timber.d("User: %s", Gson().toJson(user));

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.register(requestBody).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<RegistrationResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<RegistrationResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        Utils.savePreferences(
                            context,
                            context.getString(R.string.token),
                            t.body()?.success?.token!!
                        )
                        Utils.savePreferences(
                            context,
                            context.getString(R.string.user),
                            t.body()?.success?.name!!
                        )
                        success.value = true
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_email_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e.message)
                    loading.value = View.GONE
                }
            })
    }

    /**
     * Method to register user.
     */
    fun login(userName: String, password: String) {
        loading.value = View.VISIBLE

        val request = JSONObject()
        try {
            request.accumulate(EMAIL, userName)
            request.accumulate(PASSWORD, password)
            Timber.d(request.toString());
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.login(requestBody).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<LoginResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<LoginResponse>) {
                    Timber.e("%d", t.code())
                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        Utils.savePreferences(
                            context,
                            context.getString(R.string.token),
                            t.body()?.success?.token!!
                        )
                        /* Utils.savePreferences(
                             context,
                             context.getString(R.string.user),
                             t.body()?.success?.name!!
                         )*/
                        success.value = true
                    } else {
                        Timber.d("fail: %s", t.body())
                        Timber.d("fail: %s", Gson().toJson(t.errorBody()))
                        Timber.e("fail: %s", t.message())
                        errrorMessage.value = context.getString(R.string.error_login_response)
                    }
                    loading.value = View.GONE
                }

                override fun onError(e: Throwable) {
                    Timber.e(e.toString())
                    loading.value = View.GONE
                }
            })
    }
}