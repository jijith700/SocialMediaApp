package com.sma.liveler.base;

import android.app.Application;

import com.sma.liveler.BuildConfig;

import timber.log.Timber;

public class Liveler extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
