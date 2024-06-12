package com.wooyj.ordermenu.data.remote.service

import com.wooyj.datalayer.data.remote.api.DogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {
    @Provides
    fun provideDogApiService(retrofit: Retrofit): DogApi = retrofit.create(DogApi::class.java)
}
