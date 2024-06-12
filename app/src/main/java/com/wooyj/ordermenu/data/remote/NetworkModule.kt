package com.wooyj.ordermenu.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Google Recommended Architecture
// Okhttp, Retrofit -> OOOService -> RemoteDataSource -> Repository ( ->  UseCase ) ->  ViewModel

// @Provides -> 3rd party library, Class Constructor fixed, Builder pattern
// @Binds -> Custom class, Class Constructor not fixed, Factory pattern

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // Okhttp
    @Singleton
    @Provides
    fun provideOkhttpClient(
        loggingInterceptor: Interceptor,
        // authInterceptor: Interceptor,
        // networkInterceptor: Interceptor,
        @ConnectTimeout connectTimeout: Long,
        // @WriteTimeout writeTimeout: Long,
        // @ReadTimeout readTimeout: Long,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    // Retrofit
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
}
