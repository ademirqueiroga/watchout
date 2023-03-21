package com.admqueiroga.data

import com.admqueiroga.data.tmdb.TmdbApiError

internal inline fun <reified T : Any> NetworkResponse<T, TmdbApiError>.getOrThrow(): T = when (this) {
    is NetworkResponse.Success -> body
    is NetworkResponse.ApiError -> throw ApiException(
        message = body.message,
        code = body.code,
        httpCode = code,
    )
    is NetworkResponse.NetworkError -> throw NetworkException(error)
    is NetworkResponse.UnknownError -> throw UnknownException(httpCode = httpCode, cause = error)
}