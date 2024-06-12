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
    @Singleton
    @Provides
    @BaseUrl
    fun provideBaseUrl(
        @ApplicationContext context: Context,
    ): String = context.getString(R.string.base_url)

    @Singleton
    @Provides
    @ConnectTimeout
    fun provideConnectTimeout(): Long = 20L
}

/*
const val CONNECT_TIMEOUT = 20L
const val WRITE_TIMEOUT = 20L
const val READ_TIMEOUT = 20L
 */
