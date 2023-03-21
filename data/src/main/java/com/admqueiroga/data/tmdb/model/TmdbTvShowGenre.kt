package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbTvShowGenre(
    val id: Long,
    val name: String?
) {

    @JsonClass(generateAdapter = true)
    data class Payload(val genres: List<TmdbTvShowGenre>)

}

