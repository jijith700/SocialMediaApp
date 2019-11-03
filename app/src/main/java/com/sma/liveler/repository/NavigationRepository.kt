package com.sma.liveler.repository

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.sma.liveler.R
import com.sma.liveler.api.ApiService
import com.sma.liveler.utils.BEARER
import com.sma.liveler.utils.GROUP_NAME
import com.sma.liveler.utils.Utils
import com.sma.liveler.vo.Group
import com.sma.liveler.vo.GroupResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber


class NavigationRepository(var context: Context) {

    var loading = MutableLiveData<Int>()
    var success = MutableLiveData<Boolean>()
    var errrorMessage = MutableLiveData<String>()
    var groups = MutableLiveData<List<Group>>()

    /**
     * Variable hold the object of ApiService and the it will initialized here.
     */
    private val apiService by lazy {
        ApiService.create()
    }

    /**
     * Method to get  all groups.
     */
    fun getGroups() {
        loading.value = View.VISIBLE

        val token = Utils.loadPreferenceString(context, context.getString(R.string.token))
        Timber.d("token = %s", token)
        apiService.getGroups(String.format(BEARER, token)).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<GroupResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<GroupResponse>) {
                    Timber.e("%d", t.code())

                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        groups.value = t.body()?.user?.groups
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
     * Method to create new group.
     */
    fun createGroup(groupName: String) {
        loading.value = View.VISIBLE

        val request = JSONObject()
        try {
            request.accumulate(GROUP_NAME, groupName)
            Timber.d(request.toString());
        } catch (e: JSONException) {
            Timber.e(e.toString())
        }

        val requestBody = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            request.toString()
        )

        apiService.createGroup(requestBody).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : DisposableObserver<Response<GroupResponse>>() {
                override fun onComplete() {
                    Timber.e("Complete")
                }

                override fun onNext(t: Response<GroupResponse>) {
                    Timber.e("%d", t.code())
                    if (t.code() == 200) {
                        Timber.d("success: %s", t.body())
                        groups.value = t.body()?.user?.groups
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
                    Timber.e(e.toString())
                    loading.value = View.GONE
                }
            })
    }
}