package com.wooyj.ordermenu.data.remote.interceptor

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DebugInterceptorModule {
    // Http Logging Interceptor
    // Debugging 용도로 사용
    @Singleton
    @Provides // @Binds
    fun provideBodyLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor { message ->
            if (message.length < 16 * 1024 * 1024) {
                Timber.tag("NetworkModule : ").d(message)
            }
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
}
