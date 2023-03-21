package com.admqueiroga.data.tmdb

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbApiError(
    @Json(name = "status_message")
    val message: String?,
    @Json(name = "status_code")
    val code: Int?
)