package com.admqueiroga.discover

import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.TmdbApiService
import com.admqueiroga.data.tmdb.model.TmdbContent
import com.admqueiroga.data.tmdb.model.TmdbMovie
import com.admqueiroga.data.tmdb.model.TmdbTvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FreeToWatchRepository {

    // TODO
    private val tmdb = TmdbApiClient()
    private val movies = tmdb.movies
    private val tvShows = tmdb.tvShows

    private fun List<TmdbMovie>.toTmdbContentMovies(): List<TmdbContent> {
        return map {
            TmdbContent(
                id = it.id,
                title = it.title,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                overview = it.overview,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                genreIds = it.genreIds,
                adult = it.adult ?: false,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                popularity = it.popularity,
                video = it.video ?: false,
                mediaType = "video",
            )
        }
    }

    private fun List<TmdbTvShow>.toTmdbContentTvShows(): List<TmdbContent> {
        return map {
            TmdbContent(
                id = it.id,
                title = it.name,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                overview = it.overview,
                releaseDate = it.firstAirDate,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                genreIds = it.genreIds ?: emptyList(),
                adult = false,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalName,
                popularity = it.popularity,
                video = false,
                mediaType = "tv",
            )
        }
    }

    suspend fun getFreeToWatchMovies(): List<TmdbContent> {
        return withContext(Dispatchers.IO) {
            (movies.discover(
                1,
                genres = null,
                monetizationType = TmdbApiService.V3.MonetizationType.Free
            ) as? NetworkResponse.Success)?.body?.results?.toTmdbContentMovies() ?: emptyList()
        }
    }

    suspend fun getFreeToWatchTvShows(): List<TmdbContent> {
        return withContext(Dispatchers.IO) {
            (tvShows.discover(
                1,
                genres = null,
                monetizationType = TmdbApiService.V3.MonetizationType.Free
            ) as? NetworkResponse.Success)?.body?.results?.toTmdbContentTvShows() ?: emptyList()
        }
    }
}