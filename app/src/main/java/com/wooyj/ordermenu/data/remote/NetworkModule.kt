package com.wooyj.ordermenu.data.remote

import com.wooyj.ordermenu.data.remote.call.ResultCallAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    // OKHttp -> Network 통신 -> String
    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ConnectionTimeOut connectionTimeOut: Long,
        @ReadTimeOut readTimeOut: Long,
        @WriteTimeOut writeTimeOut: Long,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .writeTimeout(readTimeOut, TimeUnit.SECONDS)
            .readTimeout(writeTimeOut, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

    // Retrofit -> (OKHttp -> Network 통신 -> String) -> JSON / XML -> Object
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
            .addCallAdapterFactory(ResultCallAdapter.Factory())
            .build()
}
