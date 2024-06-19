package com.wooyj.ordermenu

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.wooyj.ordermenu.data.USER_INFO_PREF_NAME
import dagger.hilt.android.HiltAndroidApp

val Context.userPrefDataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_INFO_PREF_NAME,
)

@HiltAndroidApp
class OrderMenuApp : Application()
