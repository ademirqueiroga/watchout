package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbMovieGenre(
    val id: Long,
    val name: String?
) {

    @JsonClass(generateAdapter = true)
    data class Payload(val genres: List<TmdbMovieGenre>)

}

