package com.wooyj.ordermenu.data.remote

import android.content.Context
import com.wooyj.ordermenu.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkSettingModule {
    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(
        @ApplicationContext context: Context,
    ) = context.getString(R.string.base_url)

    @Provides
    @Singleton
    @ConnectionTimeOut
    fun provideConnectionTimeOut(): Long = 20L

    @Provides
    @Singleton
    @ReadTimeOut
    fun provideReadTimeOut(): Long = 20L

    @Provides
    @Singleton
    @WriteTimeOut
    fun provideWriteTimeOut(): Long = 20L
}
