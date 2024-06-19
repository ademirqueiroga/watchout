package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbSession(
    val success: Boolean,
    @Json(name = "session_id")
    val sessionId: String
)