package com.wooyj.ordermenu.data.remote.interceptor

import android.content.Context
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.net.ssl.HttpsURLConnection

class TokenAuthenticator(
    private val context: Context,
    private val authJsPublicService: AuthJsPublicService,
) : Interceptor {
    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response =
        runBlocking {
            val request = chain.request()

            if (request.headers["NO-AUTH"] == "true") {
                val temp =
                    request.headers
                        .newBuilder()
                        .removeAll("NO-AUTH")
                        .build()
                val newRequest = request.newBuilder().headers(temp).build()
                return@runBlocking chain.proceed(newRequest)
            }

            val accessToken = App.prefManager.token.accessToken

            val response = chain.proceedWithToken(request, accessToken)

            if (response.code != HttpsURLConnection.HTTP_UNAUTHORIZED || accessToken.isNullOrEmpty()) {
                return@runBlocking response
            }
            val token =
                mutex.withLock {
                    val accessToken = App.prefManager.token.accessToken
                    val refreshToken = App.prefManager.token.refreshToken

                    if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
                        return@withLock response
                    }

                    val token = authJsPublicService.postTokenRefresh(ReqRefreshToken(refreshToken!!)).execute()
                    return@withLock token
                }

            val newAccessToken = token.accessToken
            val newRefreshToken = token.refreshToken

            return@runBlocking if (newAccessToken.isNullOrEmpty() || newRefreshToken.isNullOrEmpty()) {
                App.prefManager.token.accessToken = null
                App.prefManager.token.refreshToken = null
                return@runBlocking response
            } else {
                // 토큰이 있는 경우
                App.prefManager.token.accessToken = newAccessToken
                App.prefManager.token.refreshToken = newRefreshToken
                chain.proceedWithToken(request, newAccessToken)
            }
        }

    private fun Interceptor.Chain.proceedWithToken(
        request: Request,
        accessToken: String?,
    ): Response {
        if (accessToken.isNullOrEmpty()) {
            return proceed(request)
        }
        val request =
            request
                .newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()

        return proceed(request)
    }
}
