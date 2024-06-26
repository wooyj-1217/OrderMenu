package com.wooyj.ordermenu.data.remote.call

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapter(
    private val type: Type,
) : CallAdapter<Type, Call<Result<Type>>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call)

    class Factory : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotation: Array<out Annotation>,
            retrofit: Retrofit,
        ): CallAdapter<*, *>? {
            val returnTypeClass = getRawType(returnType)
            if (returnTypeClass != Call::class.java) {
                return null
            }

            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            val rawType = getRawType(callType)
            if (rawType != Result::class.java) {
                return null
            }

            val resultType = getParameterUpperBound(0, callType as ParameterizedType)
            return ResultCallAdapter(resultType)
        }
    }
}
