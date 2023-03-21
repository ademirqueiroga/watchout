package com.admqueiroga.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

internal class NetworkResponseAdapter<T : Any, E : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>
) : CallAdapter<T, Call<NetworkResponse<T, E>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<NetworkResponse<T, E>> {
        return NetworkResponseCall(call, errorBodyConverter)
    }

}