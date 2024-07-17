package com.wooyj.ordermenu.app.init

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class FirebaseInit : Initializer<Unit> {
    override fun create(context: Context) {
        FirebaseCrashlytics.getInstance()
        Timber.d("Firebase Init")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(TimberInit::class.java)
}
