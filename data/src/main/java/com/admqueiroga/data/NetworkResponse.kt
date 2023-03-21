package com.admqueiroga.data

import okhttp3.Headers
import java.io.IOException

sealed class NetworkResponse<out T : Any, out E : Any> {

    val isSuccess: Boolean
        get() = this is Success

    data class Success<T : Any>(
        val body: T,
        val headers: Headers,
        val httpCode: Int
    ) : NetworkResponse<T, Nothing>()

    data class ApiError<E : Any>(val body: E, val code: Int) : NetworkResponse<Nothing, E>()

    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    data class UnknownError(
        val error: Throwable = Exception("Unknown error"),
        val httpCode: Int? = null
    ) : NetworkResponse<Nothing, Nothing>()

}