package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbMovieImages(
    val id: Int,
    val backdrops: List<Backdrop>,
    val posters: List<Poster>
) {
    @JsonClass(generateAdapter = true)
    data class Backdrop(
        @Json(name = "file_path")
        val filePath: String
    )
    @JsonClass(generateAdapter = true)
    data class Poster(
        @Json(name = "file_path")
        val filePath: String
    )
}