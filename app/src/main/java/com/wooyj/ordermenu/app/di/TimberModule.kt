package com.wooyj.ordermenu.app.di

import android.content.Context
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.wooyj.ordermenu.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimberModule {
    // Debug
    @Provides
    @Singleton
    fun provideTimberTreeDebug(
        @ApplicationContext context: Context,
    ): Timber.Tree =
        object : Timber.DebugTree() {
            private val tag = context.getString(R.string.app_name)

            override fun createStackElementTag(element: StackTraceElement): String =
                "(${element.fileName}:${element.lineNumber})#${element.methodName}"

            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                t: Throwable?,
            ) {
                super.log(priority, this.tag, "$tag - $message")
            }
        }

    @Provides
    @Singleton
    fun provideTimberTreeRelease(
        @ApplicationContext context: Context,
    ): Timber.Tree =
        object : Timber.Tree() {
            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                t: Throwable?,
            ) {
                when (priority) {
                    Log.VERBOSE,
                    Log.DEBUG,
                    Log.INFO,
                    -> Unit

                    Log.WARN -> {
                        FirebaseCrashlytics.getInstance().log("Warning - $message")
                    }

                    Log.ERROR -> {
                        FirebaseCrashlytics.getInstance().log("Error - $message")
                        t?.let { FirebaseCrashlytics.getInstance().recordException(it) }
                    }
                }
            }
        }
}
