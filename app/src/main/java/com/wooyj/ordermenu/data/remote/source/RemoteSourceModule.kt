package com.wooyj.ordermenu.data.remote.source

import com.wooyj.ordermenu.data.source.DogDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteSourceModule {
//    @Provides
//    fun provideRemoteSource(apiService: DogApi): DogRemoteDataSource = DogRemoteDataSource(apiService)

    @Binds
    abstract fun bindDogRemoteDataSource(impl: DogRemoteDataSource): DogDataSource
}
