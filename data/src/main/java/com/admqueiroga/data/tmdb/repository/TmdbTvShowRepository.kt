package com.admqueiroga.data.tmdb.repository

import com.admqueiroga.data.tmdb.TmdbApiService
import com.admqueiroga.data.tmdb.model.*

class TmdbTvShowRepository(private val api: TmdbApiService.V3.TvShows) {

    suspend fun genres(
        language: TmdbLanguage = TmdbLanguage.EN_US,
    ) = api.genres(language.value)

    suspend fun shows(
        page: Int,
        genres: List<TmdbMovieGenre>?,
    ) = api.discover(page, genres?.joinToString(separator = "|") { it.id.toString() })

    suspend fun shows(
        page: Int,
        vararg genres: Long,
    ) = api.discover(page, genres.joinToString(separator = "|"))

}