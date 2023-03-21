package com.admqueiroga.data.repository

import androidx.room.withTransaction
import com.admqueiroga.data.*
import com.admqueiroga.data.model.GenreMovieCrossRef
import com.admqueiroga.data.model.GenreWithMovies
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.local.mapToMovie
import com.admqueiroga.data.model.Movie
import com.admqueiroga.data.tmdb.TmdbApiService

class MovieRepositoryImpl(
    private val db: MovieDb,
    private val api: TmdbApiService.V3.Movies,
) : MovieRepository {

    private val movieDao = db.movieDao()
    private val genreMovieCrossRef = db.genreMovieCrossRefDao()
    private val detailsDao = db.movieDetailsDao()

    override suspend fun insert(movie: Movie) {
        movieDao.insert(movie)
    }

    override suspend fun insert(movies: List<Movie>) {
        movieDao.insert(movies)
    }

    override suspend fun insert(details: Movie.Details) {
        detailsDao.insertAll(listOf(details))
    }

    override suspend fun details(movieId: Long): Movie.Details? {
        return detailsDao.get(movieId)
    }

    override suspend fun getByGenre(genreId: Long): GenreWithMovies? {
        val cached = movieDao.movies(genreId)
        val dirtyCache = cached?.movies?.isEmpty() == true
        if (dirtyCache) {
            val moviesPage = api.find(page = 1, genres = genreId.toString()).getOrThrow()
            val movies = moviesPage.results.map(::mapToMovie)
            val crossRefs = movies.map { movie ->
                GenreMovieCrossRef(genreId, movie.id)
            }
            db.withTransaction {
                genreMovieCrossRef.insert(crossRefs)
                movieDao.insert(movies)
            }
        }
        return movieDao.movies(genreId)
    }


}