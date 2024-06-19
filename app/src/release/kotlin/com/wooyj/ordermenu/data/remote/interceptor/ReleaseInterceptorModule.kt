package com.wooyj.ordermenu.data.remote.interceptor

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReleaseInterceptorModule {
    @Provides
    @Singleton
    fun provideNoneLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { }.setLevel(HttpLoggingInterceptor.Level.NONE)
}
