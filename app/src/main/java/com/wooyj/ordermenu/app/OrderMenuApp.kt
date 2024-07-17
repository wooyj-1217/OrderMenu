package com.wooyj.ordermenu.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class OrderMenuApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.d("App Start")
    }
}
