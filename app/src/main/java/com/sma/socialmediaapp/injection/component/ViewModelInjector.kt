package com.sma.socialmediaapp.injection.component

import com.sma.socialmediaapp.injection.module.NetworkModule
import com.sma.socialmediaapp.ui.home.HomeViewModel
import com.sma.socialmediaapp.ui.login.LoginViewModel
import com.sma.socialmediaapp.ui.registration.RegisterViewModel
import com.sma.socialmediaapp.ui.timeline.TimelineViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param loginViewModel LoginViewModel in which to inject the dependencies
     */
    fun inject(loginViewModel: LoginViewModel)

    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param registerViewModel RegisterViewModel in which to inject the dependencies
     */
    fun inject(registerViewModel: RegisterViewModel)

    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param homeViewModel HomeViewModel in which to inject the dependencies
     */
    fun inject(homeViewModel: HomeViewModel)

    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param timelineViewModel TimelineViewModel in which to inject the dependencies
     */
    fun inject(timelineViewModel: TimelineViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}