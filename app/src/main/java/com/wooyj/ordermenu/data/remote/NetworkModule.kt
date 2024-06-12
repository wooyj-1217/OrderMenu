package com.wooyj.ordermenu.data.remote

import com.wooyj.ordermenu.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// Google Recommended Architecture
// Okhttp, Retrofit -> OOOService -> RemoteDataSource -> Repository ( ->  UseCase ) ->  ViewModel

// @Provides -> 3rd party library, Class Constructor fixed, Builder pattern
// @Binds -> Custom class, Class Constructor not fixed, Factory pattern

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // Http Logging Interceptor
    @Singleton
    @Provides // @Binds
    fun provideBodyLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            if (message.length < 16 * 1024 * 1024) {
                Timber.tag("NetworkModule : ").d(message)
            }
        }.setLevel(HttpLoggingInterceptor.Level.BODY)

    // Okhttp
    @Singleton
    @Provides
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    // Retrofit
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
}
