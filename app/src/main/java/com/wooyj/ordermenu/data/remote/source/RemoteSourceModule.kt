package com.wooyj.ordermenu.data.remote.source

import com.wooyj.ordermenu.source.DogDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteSourceModule {
    @Binds
    abstract fun bindDogRemoteDataSource(impl: DogRemoteDataSource): DogDataSource
}
