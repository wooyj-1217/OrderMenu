package com.wooyj.ordermenu.data.remote.interceptor

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebugInterceptorModule {

    @Provides
    @Singleton
    fun provideBodyLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            if (message.length < 16 * 1024 * 1024) {
                Timber.tag("NetworkModule : ").d(message)
            }
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
}
