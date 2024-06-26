package com.admqueiroga.data.local

import com.admqueiroga.data.tmdb.model.TmdbMovie
import com.admqueiroga.data.tmdb.model.TmdbMovieDetail
import com.admqueiroga.data.tmdb.model.TmdbMovieGenre
import com.admqueiroga.data.tmdb.model.TmdbTvShowGenre
import com.admqueiroga.data.model.*
import com.admqueiroga.data.tmdb.model.TmdbTvShow

internal fun mapToTvShow(tvShow: TmdbTvShow) = TvShow(
    id = tvShow.id,
    name = tvShow.name ?: "",
    posterPath = tvShow.posterPath ?: "",
    voteAverage = tvShow.voteAverage ?: -1F,
    firstAirDate = tvShow.firstAirDate.orEmpty(),
)

internal fun mapToMovie(movie: TmdbMovie) = Movie(
    id = movie.id,
    title = movie.title ?: "",
    posterPath = movie.posterPath ?: "",
    voteAverage = movie.voteAverage ?: -1F,
    releaseDate = movie.releaseDate.orEmpty(),
)

internal fun mapToMovieDetails(details: TmdbMovieDetail) = Movie.Details(
    id = details.id,
    posterPath = details.posterPath ?: "",
    title = details.title,
    backdropPath = details.backdropPath ?: "",
    overview = details.overview ?: "",
    releaseDate = details.releaseDate ?: "",
    hasVideo = details.video,
    voteAverage = details.voteAverage,
    voteCount = details.voteCount,
    budget = details.budget,
    runtime = details.runtime,
)

internal fun mapToMovieGenre(genre: TmdbMovieGenre) = MovieGenre(
    id = genre.id,
    name = genre.name ?: ""
)

internal fun mapToTvShowGenre(genre: TmdbTvShowGenre) = TvShowGenre(
    id = genre.id,
    name = genre.name ?: ""
)