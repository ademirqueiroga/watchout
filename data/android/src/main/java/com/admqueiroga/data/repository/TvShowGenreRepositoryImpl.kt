package com.admqueiroga.data.repository

import com.admqueiroga.data.getOrThrow
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.local.mapToMovieGenre
import com.admqueiroga.data.local.mapToTvShowGenre
import com.admqueiroga.data.model.TvShowGenre
import com.admqueiroga.data.tmdb.TmdbApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart

class TvShowGenreRepositoryImpl(
    val db: MovieDb,
    val tmdbApi: TmdbApiService.V3.TvShows,
) : TvShowGenreRepository {

    private val tvShowGenreDao = db.tvShowGenreDao()

    override suspend fun insert(genre: List<TvShowGenre>) =
        tvShowGenreDao.insert(genre)

    override suspend fun insert(genre: TvShowGenre) =
        tvShowGenreDao.insert(genre)

    override fun flow(): Flow<List<TvShowGenre>> = tvShowGenreDao.flow()
        .onStart {
            val dirtyCache = true // TODO
            if (dirtyCache) {
                val genresResponse = tmdbApi.genres().getOrThrow()
                tvShowGenreDao.insert(genresResponse.genres.map(::mapToTvShowGenre))
            }
        }
        .distinctUntilChanged()
}