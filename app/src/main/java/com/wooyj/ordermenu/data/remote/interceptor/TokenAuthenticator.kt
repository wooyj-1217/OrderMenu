package com.wooyj.ordermenu.data.remote.interceptor

import android.content.Context
import auth_js.AuthJs
import com.glowdayz.glowmee.GlowApp
import com.glowdayz.glowmee.R
import com.glowdayz.glowmee.model.request.ReqGuestToken
import com.glowdayz.glowmee.model.request.ReqRefreshToken
import com.glowdayz.glowmee.network.service.AuthJsPublicService
import com.glowdayz.glowmee.utils.Utils
import com.glowdayz.glowmee.utils.extension.getDecodeData
import com.glowdayz.glowmee.utils.login.UserUtil
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class TokenInterceptor @Inject constructor(
    context: Context,
    private val authJsPublicService: Lazy<AuthJsPublicService>,
) : Interceptor {

    private val mutex = lazy { Mutex() }

    override fun intercept(chain: Interceptor.Chain): Response =
        runBlocking {
            // Request에 토큰을 추가하는 작업
            val request = chain.request()

            // No Auth의 경우 token을 넣지 않는다.
            if (request.headers[context.getString(R.string.header_no_auth)] == context.getString(R.string._true)) {
                val temp =
                    request.headers
                        .newBuilder()
                        .removeAll(context.getString(R.string.header_no_auth))
                        .build()
                val newRequest = request.newBuilder().headers(temp).build()
                return@runBlocking chain.proceed(newRequest)
            }

            // accessToken 가져오기
            val accessToken = GlowApp.prefManager.token.accessToken
            val refreshToken = GlowApp.prefManager.token.refreshToken

            // response 받아오기
            val response = chain.proceedWithToken(request, accessToken)

            if (response.code != HttpsURLConnection.HTTP_UNAUTHORIZED) {
                // HTTP_UNAUTHORIZED 응답이 아닌 경우 그대로 return
                return@runBlocking response
            }

            // 응답 횟수 확인(이전에 몇번이나 다시 시도 했는지)
            val responseCount = responseCount(response)
            Timber.e("TokenInterceptor Response count: $responseCount")

            // 갱신 토큰 값 가져오기
            val newToken =
                if (responseCount > context.resources.getInteger(R.integer.max_response_count)) {
                    // 5회가 넘어갈 때 : 로그아웃 처리(새로운 토큰도, Guest 토큰도 못가져오는 상태)
                    UserUtil.logout()
                    return@runBlocking response
                } else if (responseCount > context.resources.getInteger(R.integer.guest_token_count)) {
                    // 3회가 넘어갈 때 : Guest 토큰으로 갱신(로그아웃 처리됨)
                    getGuestToken()
                } else {
                    if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
                        // 기존 access token 및 refresh token이 없는 경우 : Guest 토큰으로 갱신
                        getGuestToken()
                    } else {
                        if (UserUtil.isLogin()) {
                            // 로그인 : 새 access token 값 가져옴
                            getNewAccessToken(refreshToken)
                        } else {
                            // 로그인이 안된 경우 : Guest 토큰으로 갱신
                            getGuestToken()
                        }
                    }
                }

            val newAccessToken = if (newToken is AuthJs.PostAuthTokenRes) newToken.accessToken else null
            val newRefreshToken = if (newToken is AuthJs.PostAuthTokenRes) newToken.refreshToken else null

            return@runBlocking if (newAccessToken.isNullOrEmpty() || newRefreshToken.isNullOrEmpty()) {
                UserUtil.logout()
                response // 기존 response 그대로 보냄
            } else {
                // 토큰이 있는 경우 (header에 추가)
                GlowApp.prefManager.token.accessToken = newAccessToken
                GlowApp.prefManager.token.refreshToken = newRefreshToken
                // 기존 response 닫아주기
                response.close()
                // 새로 response 만들어서 보내기
                chain.proceedWithToken(request, newAccessToken)
            }
        }

    private suspend fun getGuestToken(): AuthJs.PostAuthTokenRes? = mutex.withLock {
        if(UserUtil.isLogin()) {
            UserUtil.logout()
        }
        val res =
            authJsPublicService.get().postGuestToken(ReqGuestToken(Utils.getAndroidId(context)))
                .execute()
        if (res.isSuccessful) {
            res.body()?.data?.getDecodeData()?.let {
                return@withLock AuthJs.PostAuthTokenRes.parseFrom(it)
            }
        } else {
            return@withLock null
        }
    }

    private suspend fun getNewAccessToken(refreshToken: String): AuthJs.PostAuthTokenRes? =
        mutex.withLock {
            val res =
                authJsPublicService.get().postTokenRefresh(ReqRefreshToken(refreshToken)).execute()
            if (res.isSuccessful) {
                res.body()?.data?.getDecodeData()?.let {
                    return@withLock AuthJs.PostAuthTokenRes.parseFrom(it)
                }
            } else {
                return@withLock null
            }
        }

    private fun Interceptor.Chain.proceedWithToken(
        request: Request,
        accessToken: String?,
    ): Response {
        if (accessToken.isNullOrEmpty()) {
            return proceed(request)
        }
        val req =
            request
                .newBuilder()
                .header(
                    context.getString(R.string.authorization),
                    context.getString(R.string.bearer_string, accessToken)
                )
                .build()
        return proceed(req)
    }

    /**
     *
     * access token을 가지고 통신하는 api 에서 token 만료로 실패한 횟수
     *
     * @author ssryu
     * @since 3.0.0
     *
     * @param response 서버에서 받아온 response
     * @return access token 통신을 시도하고 실패한 횟수(Int)
     *
     */
    private fun responseCount(response: Response): Int {
        var result = 1
        var curResponse: Response? = response
        while (curResponse?.priorResponse != null) {
            result++
            curResponse = curResponse.priorResponse
        }
        return result
    }
}
