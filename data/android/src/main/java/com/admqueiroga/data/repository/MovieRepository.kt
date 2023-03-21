package com.admqueiroga.data.repository

import com.admqueiroga.data.model.GenreWithMovies
import com.admqueiroga.data.model.Movie

interface MovieRepository {
    suspend fun insert(movie: Movie)
    suspend fun insert(movies: List<Movie>)
    suspend fun insert(details: Movie.Details)
    suspend fun details(movieId: Long): Movie.Details?
    suspend fun getByGenre(genreId: Long): GenreWithMovies?
}