package com.sma.socialmediaapp.base

import androidx.lifecycle.ViewModel
import com.sma.socialmediaapp.injection.component.ViewModelInjector
import com.sma.socialmediaapp.injection.module.NetworkModule
import com.sma.socialmediaapp.ui.home.HomeViewModel
import com.sma.socialmediaapp.ui.login.LoginViewModel
import com.sma.socialmediaapp.ui.registration.RegisterViewModel
import com.sma.socialmediaapp.ui.timeline.TimelineViewModel


abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LoginViewModel -> injector.inject(this)
            is RegisterViewModel -> injector.inject(this)
            is HomeViewModel -> injector.inject(this)
            is TimelineViewModel -> injector.inject(this)
        }
    }
}