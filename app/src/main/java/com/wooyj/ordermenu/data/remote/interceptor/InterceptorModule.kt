package com.wooyj.ordermenu.data.remote.interceptor

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {
    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val request =
                chain
                    .request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", System.getProperty("http.agent")!!)
                    .build()

            chain.proceed(request)
        }
}
