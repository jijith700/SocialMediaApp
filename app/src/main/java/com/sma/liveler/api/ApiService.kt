package com.sma.liveler.api

import com.sma.liveler.utils.BASE_URL
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

/**
 * Class to initialize the retrofit instance to access the Web APIs.
 */
class ApiService {
    companion object {

        /**
         * Method to create the retrofit instance to access the APIs.
         *
         * @return instance of WebApiListener.
         */
        fun create(): WebApiListener = create(HttpUrl.parse(BASE_URL)!!)

        fun create(httpUrl: HttpUrl): WebApiListener {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Timber.d("API %s", it)
            })

            /*val interceptor = Interceptor {
                val original = it.request()
                val response = it.proceed(original)
                Timber.d( "Code : %d", response.code())
                response
            }*/
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WebApiListener::class.java)
        }
    }
}