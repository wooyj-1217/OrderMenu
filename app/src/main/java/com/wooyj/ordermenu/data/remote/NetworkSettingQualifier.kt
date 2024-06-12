@file:Suppress("ktlint:standard:filename")

package com.wooyj.ordermenu.data.remote

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ConnectTimeout
