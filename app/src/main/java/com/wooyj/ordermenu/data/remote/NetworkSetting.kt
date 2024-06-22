package com.wooyj.ordermenu.data.remote

data class NetworkSetting(
    val connectionTimeOut: Long,
    val readTimeOut: Long,
    val writeTimeOut: Long,
)
