package com.wooyj.ordermenu.app.init

import android.content.Context
import androidx.startup.Initializer
import com.wooyj.ordermenu.app.init.di.InitializerEntryPoint

class DependcyGraphInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
