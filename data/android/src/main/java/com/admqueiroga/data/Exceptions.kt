package com.admqueiroga.data

class ApiException(
    message: String?,
    val code: Int?,
    val httpCode: Int?,
) : Exception(message)

class NetworkException(cause: Throwable) : Exception(cause)

class UnknownException(
    val httpCode: Int?,
    cause: Throwable,
) : Exception(cause)