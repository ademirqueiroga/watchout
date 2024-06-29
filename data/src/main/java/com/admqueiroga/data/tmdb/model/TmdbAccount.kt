package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbAccount(
    val id: Long,
    val username: String,
    val name: String,
)