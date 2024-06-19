package com.admqueiroga.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.admqueiroga.data.getOrThrow
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.local.mapToMovie
import com.admqueiroga.data.model.GenreMovieCrossRef
import com.admqueiroga.data.model.Movie
import com.admqueiroga.data.tmdb.TmdbApiService
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val genreId: Long,
    private val db: MovieDb,
    private val api: TmdbApiService.V3.Movies,
) : RemoteMediator<Int, Movie>() {

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
        state: PagingState<Int, Movie>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    db.withTransaction { runCatching {
                        genreDao.remoteKeyByGenre(genreId)
                    } }.getOrNull()?.nextKey ?: 1
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull() ?: return MediatorResult.Success(true)
                    val remoteKey = db.withTransaction {
                        genreDao.remoteKeyByGenre(genreId)
                    }
                    remoteKey.nextKey
                }
            }
            val moviesPage = api.discover(loadKey, genreId.toString()).getOrThrow()
            val movies = moviesPage.results.map(::mapToMovie)
            val crossRefs = movies.map { movie ->
                GenreMovieCrossRef(genreId, movie.id)
            }
            db.withTransaction {
                // TODO: Unecessary code, but good practice?
//                if (loadType == LoadType.REFRESH) {
//                    genreMovieCrossRefDao.delete(crossRefs)
//                    genreDao.deleteRemoteKeyByGenre(genreId)
//                }
                movieDao.insert(movies)
                genreMovieCrossRefDao.insert(crossRefs)
                genreDao.insertOrReplace(
                    GenreMovieRemoteKey(
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