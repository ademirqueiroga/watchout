package com.admqueiroga.data.tmdb.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbPerson(
    @Json(name = "profile_path")
    val profilePath: String?,
    val adult: Boolean?,
    val id: Long,
    val name: String?,
    val popularity: Float?,
) {

    @JsonClass(generateAdapter = true)
    data class Images(val profiles: List<Profile>)
    
    @JsonClass(generateAdapter = true)
    data class Profile(
        @Json(name = "aspect_ratio")
        val aspectRation: Float,
        @Json(name = "file_path")
        val filePath: String?,
        val height: Int,
        @Json(name = "vote_average")
        val voteAverage: Float,
        @Json(name = "vote_count")
        val voteCount: Int,
        val width: Int
    )

}