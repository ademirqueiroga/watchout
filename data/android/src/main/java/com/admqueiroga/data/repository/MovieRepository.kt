package com.admqueiroga.data.repository

import com.admqueiroga.data.model.GenreWithMovies
import com.admqueiroga.data.model.Movie
import com.admqueiroga.data.tmdb.TmdbApiService

interface MovieRepository {
    suspend fun insert(movie: Movie)
    suspend fun insert(movies: List<Movie>)
    suspend fun insert(details: Movie.Details)
    suspend fun details(movieId: Long): Movie.Details?
    suspend fun getByGenre(genreId: Long): GenreWithMovies?
    suspend fun trending(timeWindow: TmdbApiService.V3.TimeWindow): List<Movie>
}