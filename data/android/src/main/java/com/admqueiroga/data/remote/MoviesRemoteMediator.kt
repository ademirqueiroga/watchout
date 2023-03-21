package com.admqueiroga.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.admqueiroga.data.tmdb.TmdbApiService
import com.admqueiroga.data.model.GenreMovieCrossRef
import com.admqueiroga.data.model.GenreWithMovies
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.local.mapToMovie
import com.admqueiroga.data.getOrThrow
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val genreId: Long,
    private val db: MovieDb,
    private val api: TmdbApiService.V3.Movies,
) : RemoteMediator<Int, GenreWithMovies>() {

    private val movieDao = db.movieDao()
    private val genreDao = db.movieGenreDao()
    private val genreMovieCrossRefDao = db.genreMovieCrossRefDao()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
        val remoteKey = try {
            genreDao.remoteKeyByGenre(genreId)
        } catch (e: Exception) {
            null
        }
        return when (remoteKey != null && System.currentTimeMillis() - remoteKey.timestamp >= cacheTimeout) {
            true -> InitializeAction.SKIP_INITIAL_REFRESH
            else -> InitializeAction.LAUNCH_INITIAL_REFRESH
        }.also {
            Log.d("MovieRemoteMediator", it.toString())
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GenreWithMovies>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        genreDao.remoteKeyByGenre(genreId)
                    }
                    if (state.lastItemOrNull() == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextKey
                }
            }
            val moviesPage = api.find(loadKey, genreId.toString()).getOrThrow()
            val movies = moviesPage.results.map(::mapToMovie)
            val crossRefs = movies.map { movie ->
                GenreMovieCrossRef(genreId, movie.id)
            }
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    genreMovieCrossRefDao.delete(crossRefs)
                    genreDao.deleteRemoteKeyByGenre(genreId)
                }
                movieDao.insert(movies)
                genreMovieCrossRefDao.insert(crossRefs)
                genreDao.insertOrReplace(
                    com.admqueiroga.data.GenreMovieRemoteKey(
                        genreId = genreId,
                        timestamp = System.currentTimeMillis(),
                        nextKey = moviesPage.page + 1
                    )
                )
            }
            Log.d("MovieRemoteMediator", "hasNext:${moviesPage.hasNext}")
            MediatorResult.Success(endOfPaginationReached = !moviesPage.hasNext)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}