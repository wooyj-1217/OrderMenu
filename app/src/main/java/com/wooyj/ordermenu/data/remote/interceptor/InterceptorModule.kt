package com.wooyj.ordermenu.data.remote.interceptor

import android.content.Context
import com.wooyj.ordermenu.data.remote.HeaderInterceptor
import com.wooyj.ordermenu.data.remote.TokenInterceptor
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {
    @Provides
    @Singleton
    @HeaderInterceptor
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

    @Provides
    @Singleton
    @TokenInterceptor
    fun provideTokenInterceptor(
        @ApplicationContext context: Context,
        authJsPublicService: Lazy<AuthJsPublicService>,
    ) = TokenAuthenticator(context, authJsPublicService)

}
