package com.wooyj.ordermenu.data.remote.call

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultCall<T : Any>(
    private val call: Call<T>,
) : Call<Result<T>> {
    override fun clone(): Call<Result<T>> = ResultCall(call.clone())

    override fun execute(): Response<Result<T>> {
        val response = call.execute()
        return if (response.isSuccessful && response.body() != null) {
            Response.success(Result.success(response.body()!!))
        } else {
            Response.success(Result.failure(Throwable(response.message())))
        }
    }

    override fun enqueue(callBack: Callback<Result<T>>) {
        call.enqueue(
            object : Callback<T> {
                override fun onResponse(
                    call: Call<T>,
                    response: Response<T>,
                ) {
                    val networkCall =
                        if (response.isSuccessful && response.body() != null) {
                            Result.success(response.body()!!)
                        } else {
                            Result.failure(Throwable(response.message()))
                        }
                    callBack.onResponse(this@ResultCall, Response.success(networkCall))
                }

                override fun onFailure(
                    call: Call<T>,
                    t: Throwable,
                ) {
                    callBack.onResponse(this@ResultCall, Response.success(Result.failure(t)))
                }
            },
        )
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()
}
