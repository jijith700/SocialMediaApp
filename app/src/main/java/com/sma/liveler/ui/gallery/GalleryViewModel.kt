package com.sma.liveler.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma.liveler.api.WebApiListener
import javax.inject.Inject

class GalleryViewModel() : ViewModel() {

    var TAG = GalleryViewModel::class.java.simpleName

    @Inject
    lateinit var webApiListener: WebApiListener

    val userName: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()
    val loading: MutableLiveData<Int> = MutableLiveData()


    fun onclickLogin(userName: String, password: String) {

    }
}