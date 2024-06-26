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
    fun provideNetworkSetting(
        @ApplicationContext context: Context,
    ) = NetworkSetting(
        connectionTimeOut = context.resources.getInteger(R.integer.connection_time_out).toLong(),
        readTimeOut = context.resources.getInteger(R.integer.read_time_out).toLong(),
        writeTimeOut = context.resources.getInteger(R.integer.write_time_out).toLong(),
    )
}
