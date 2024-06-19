package com.admqueiroga.data.tmdb.repository

import com.admqueiroga.data.tmdb.TmdbApiService
import com.admqueiroga.data.tmdb.model.TmdbLanguage
import com.admqueiroga.data.tmdb.model.TmdbMovieGenre

class TmdbMovieRepository(
    private val api: TmdbApiService.V3.Movies,
) {

    suspend fun genres(
        language: TmdbLanguage = TmdbLanguage.EN_US,
    ) = api.genres(language.value)

    suspend fun movies(
        page: Int,
        genres: List<TmdbMovieGenre>?,
    ) = api.discover(page, genres?.joinToString(separator = "|") { it.id.toString() })

    suspend fun movies(
        page: Int,
        vararg genreIds: Long,
    ) = api.discover(page, genreIds.joinToString(separator = "|"))

    suspend fun popular(page: Int? = null) = api.popular(page)

    suspend fun detail(movieId: Long) = api.detail(movieId)

}