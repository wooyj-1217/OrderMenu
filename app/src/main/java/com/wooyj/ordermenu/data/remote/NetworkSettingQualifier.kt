package com.wooyj.ordermenu.data.remote

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class BaseUrl

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class HeaderInterceptor
