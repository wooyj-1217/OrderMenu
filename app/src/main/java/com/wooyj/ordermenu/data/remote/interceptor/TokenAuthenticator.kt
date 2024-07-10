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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import javax.net.ssl.HttpsURLConnection

// TODO("4. 순환의존성 문제")
// 단일 okhttpClient 객체를 만들고 나서 TokenInterceptor 생성했는데
// TokenInterceptor에서 AuthJsPublicService를 사용하려고 하니까
// Compile Error가 생기더라구요
//  Found a dependency cycle:
// okhttp객체 -> AuthJsPublicService(retrofit) -> TokenInterceptor -> okhttp객체
// 1. 아래와 같은 코드로 해결하긴 했는데 이게 맞는 방법인가요?
// 2. Lazy를 써서 넣으니까 왜 된건지도 잘 모르겠어요.

// context Problem
// Q. 1. TokenInterceptor에서 Context는 무슨 Context인가요?
// Q. 2. Why Context 멤버변수로 하면 안좋을까요?
// Q. 3. Context?? -> 흐름 -> 목적
// Activity -> Context -> 무언가 그릴려고
// Service -> Context -> 무언가 하려고
// BroadcastReceiver -> Context -> 무언가 받으려고
// Provider -> Context -> 무언가 주려고

// A. 2. 흐름을 왜 가져야 하는가???
// ???Context 흐름은 그때 그때 맞춰 쓰는게 좋다.

// A. 1. 그때 그때 달라요 -> Application Context

class TokenInterceptor
    @Inject
    constructor(
        context: Context,
        private val authJsPublicService: Provider<AuthJsPublicService>,
    ) : Interceptor {
        private val getString: (Int) -> String = context::getString

        private val getInteger: (Int) -> Int = context.resources::getInteger

        private val mutex by lazy { Mutex() }

        override fun intercept(chain: Interceptor.Chain): Response =
            runBlocking {
                // Request에 토큰을 추가하는 작업
                val request = chain.request()

                // No Auth의 경우 token을 넣지 않는다.
                if (request.headers[getString(R.string.header_no_auth)] == getString(R.string._true)) {
                    val temp =
                        request.headers
                            .newBuilder()
                            .removeAll(getString(R.string.header_no_auth))
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
                    if (responseCount > getInteger(R.integer.max_response_count)) {
                        // 5회가 넘어갈 때 : 로그아웃 처리(새로운 토큰도, Guest 토큰도 못가져오는 상태)
                        UserUtil.logout()
                        return@runBlocking response
                    } else if (responseCount > getInteger(R.integer.guest_token_count)) {
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

        private suspend fun getGuestToken(): AuthJs.PostAuthTokenRes? =
            mutex.withLock {
                if (UserUtil.isLogin()) {
                    UserUtil.logout()
                }
                val res =
                    authJsPublicService
                        .get()
                        .postGuestToken(ReqGuestToken(Utils.getAndroidId(context)))
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
                        getString(R.string.authorization),
                        getString(R.string.bearer_string, accessToken),
                    ).build()
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
