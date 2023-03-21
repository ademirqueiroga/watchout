package com.admqueiroga.data

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class NetworkResponseCall<T : Any, E : Any>(
    private val delegate: Call<T>,
    private val errorConverter: Converter<ResponseBody, E>,
) : Call<NetworkResponse<T, E>> {

    override fun enqueue(callback: Callback<NetworkResponse<T, E>>) {
        return delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val httpCode = response.code()
                val headers = response.headers()
                if (response.isSuccessful) {
                    when (val body = response.body()) {
                        null -> {
                            val exception =
                                Exception("Server response code:$httpCode but with null body")
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.UnknownError(exception, httpCode))
                            )
                        }
                        else -> callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(body, headers, httpCode))
                        )
                    }
                } else {
                    val error = response.errorBody()
                    val errorBody = when {
                        error == null || error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    when (errorBody) {
                        null -> {
                            val exception =
                                Exception("Server response code:$httpCode but with null errorBody")
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.UnknownError(exception, httpCode))
                            )
                        }
                        else -> callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.ApiError(errorBody, httpCode))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<T>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.NetworkError(throwable)
                    else -> NetworkResponse.UnknownError(throwable)
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }

        })
    }

    override fun execute(): Response<NetworkResponse<T, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun clone(): Call<NetworkResponse<T, E>> =
        NetworkResponseCall(delegate.clone(), errorConverter)

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout {
        return delegate.timeout()
    }

}
