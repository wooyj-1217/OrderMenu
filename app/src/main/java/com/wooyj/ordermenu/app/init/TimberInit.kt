package com.wooyj.ordermenu.app.init

import android.content.Context
import androidx.startup.Initializer
import com.wooyj.ordermenu.app.init.di.InitializerEntryPoint
import timber.log.Timber
import javax.inject.Inject

class TimberInit : Initializer<Unit> {
    @Inject
    lateinit var tree: Timber.Tree

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context).inject(this)

        Timber.plant(tree)
        Timber.d("Timber Init")
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(DependcyGraphInitializer::class.java)
}
