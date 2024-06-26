package com.wooyj.ordermenu.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @HeaderInterceptor headerInterceptor: Interceptor,
        networkSetting: NetworkSetting,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(networkSetting.connectionTimeOut, TimeUnit.SECONDS)
            .writeTimeout(networkSetting.writeTimeOut, TimeUnit.SECONDS)
            .readTimeout(networkSetting.readTimeOut, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    // TODO("base url이 여러개일 경우에는 어떻게 처리하는 것이 좋을까요?")
    // 케이스를 나누자면..
    // - auth 인증이 필요한 경우 / auth 인증 필요없는 경우
    // - 테스트서버용 url들 / 실서버용 url들
    // okhttp client는 하나로 하되 retrofit 객체를 여러개를 만들어도 괜찮은가요?
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
