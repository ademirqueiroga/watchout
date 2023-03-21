package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbPage<T>(
    val page: Int,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
    val results: List<T>
) {

    val hasNext = page < totalPages

}