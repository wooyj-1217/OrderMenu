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
            // Request에 토큰을 추가하는 작업
            val request = chain.request()

            // No Auth의 경우 token을 넣지 않는다.
            if (request.headers["NO-AUTH"] == "true") {
                val temp =
                    request.headers
                        .newBuilder()
                        .removeAll("NO-AUTH")
                        .build()
                val newRequest = request.newBuilder().headers(temp).build()
                return@runBlocking chain.proceed(newRequest)
            }

            // accessToken 가져오기
            val accessToken = App.prefManager.token.accessToken

            // response 받아오기
            val response = chain.proceedWithToken(request, accessToken)

            if (response.code != HttpsURLConnection.HTTP_UNAUTHORIZED || accessToken.isNullOrEmpty()) {
                // 정상 응답인 경우 그대로 return || accessToken이 없는 경우(Logout, 처음 사용자), 그대로 return
                return@runBlocking response
            }
            val token =
                mutex.withLock {
                    // 토큰을 가져온다
                    val accessToken = App.prefManager.token.accessToken
                    val refreshToken = App.prefManager.token.refreshToken

                    if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
                        // accessToken이 없는 경우(Logout, 처음 사용자), 그대로 return
                        return@withLock response
                    }

                    // 토큰을 갱신한다
                    val token = authJsPublicService.postTokenRefresh(ReqRefreshToken(refreshToken!!)).execute()

                    return@withLock token
                }

            val newAccessToken = token.accessToken
            val newRefreshToken = token.refreshToken

            return@runBlocking if (newAccessToken.isNullOrEmpty() || newRefreshToken.isNullOrEmpty()) {
                // 토큰이 없는 경우(Logout, 처음 사용자), 그대로 return
                App.prefManager.token.accessToken = null / ""
                App.prefManager.token.refreshToken = null / ""
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
