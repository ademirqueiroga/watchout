package com.admqueiroga.data.repository

import com.admqueiroga.data.getOrThrow
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.local.mapToMovieGenre
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.tmdb.TmdbApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart

class MovieGenreRepositoryImpl(
    db: MovieDb,
    private val tmdbApi: TmdbApiService.V3.Movies,
) : MovieGenreRepository {

    private val movieGenreDao = db.movieGenreDao()
    private val genreMovieCrossRefDao = db.genreMovieCrossRefDao()

    override suspend fun insert(genre: List<MovieGenre>) =
        movieGenreDao.insert(genre)

    override suspend fun insert(genre: MovieGenre) =
        movieGenreDao.insert(genre)

    override suspend fun get(genreId: Long): MovieGenre {
        return movieGenreDao.get(genreId = genreId)
    }

    suspend fun deleteCrossRefs(genreId: Long) {
        genreMovieCrossRefDao.deleteByGenre(genreId)
    }

    suspend fun refresh() {
        val genresResponse = tmdbApi.genres().getOrThrow()
        movieGenreDao.insert(genresResponse.genres.map(::mapToMovieGenre))
    }

    override fun flow(): Flow<List<MovieGenre>> = movieGenreDao.flow()
        .onStart {
            val dirtyCache = false // TODO: Figure out when to invalidate cache
            if (dirtyCache) {
                try {
                    refresh()
                } catch (e: Exception) {
                    // Ignore
                }
            }
        }
//        .distinctUntilChanged()

}