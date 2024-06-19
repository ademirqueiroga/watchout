package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbNewSessionRequest(
    @Json(name = "request_token")
    val requestToken: String
)