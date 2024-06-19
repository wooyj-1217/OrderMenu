package com.wooyj.ordermenu.data.remote

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class BaseUrl

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ConnectionTimeOut

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ReadTimeOut

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class WriteTimeOut
