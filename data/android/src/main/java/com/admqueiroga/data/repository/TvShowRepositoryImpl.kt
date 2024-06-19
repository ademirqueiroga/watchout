package com.admqueiroga.data.repository

import androidx.room.withTransaction
import com.admqueiroga.data.getOrThrow
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.local.mapToTvShow
import com.admqueiroga.data.model.GenreTvShowCrossRef
import com.admqueiroga.data.model.GenreWithTvShows
import com.admqueiroga.data.model.TrendingTvShow
import com.admqueiroga.data.model.TvShow
import com.admqueiroga.data.tmdb.TmdbApiService

class TvShowRepositoryImpl(
    private val db: MovieDb,
    private val api: TmdbApiService.V3.TvShows,
) : TvShowRepository {

    private val tvShowDao = db.tvShowDao()
    private val tvShowGenreDao = db.tvShowGenreDao()
    private val genreTvShowCrossRefDao = db.genreTvShowCrossRefDao()

    override suspend fun insert(tvShow: TvShow) =
        tvShowDao.insert(tvShow)

    override suspend fun insert(tvShows: List<TvShow>) =
        tvShowDao.insert(tvShows)


    override suspend fun details(tvShowId: Long): TvShow.Details? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(details: TvShow.Details) {
        TODO("Not yet implemented")
    }

    override suspend fun getByGenre(genreId: Long): GenreWithTvShows? {
        val cached = tvShowDao.tvShows(genreId)
        val dirtyCache = cached?.movies?.isEmpty() == true
        if (dirtyCache) {
            // TODO: Handle getOrThrow
            val moviesPage = api.discover(page = 1, genres = genreId.toString()).getOrThrow()
            val tvShows = moviesPage.results.map(::mapToTvShow)
            val crossRefs = tvShows.map { tvShow ->
                GenreTvShowCrossRef(genreId, tvShow.id)
            }
            db.withTransaction {
                genreTvShowCrossRefDao.insert(crossRefs)
                tvShowDao.insert(tvShows)
            }
            val genre = tvShowGenreDao.get(genreId)
            return GenreWithTvShows(genre, tvShows)
        }
        return tvShowDao.tvShows(genreId)
    }

    override suspend fun trending(timeWindow: TmdbApiService.V3.TimeWindow): List<TvShow> {
        val cachedTvShows = tvShowDao.trending(timeWindow.toString())
        // TODO: Improve cache invalidation
        val dirtyCache = cachedTvShows.isEmpty()
        if (dirtyCache) {
            // TODO: Handle exceptions
            val tvShowPage = api.trending(timeWindow).getOrThrow()
            val trendingTvShows = ArrayList<TrendingTvShow>(tvShowPage.results.size)
            val tvShows = ArrayList<TvShow>(tvShowPage.results.size)
            tvShowPage.results.forEachIndexed { index, tmdbTvShow ->
                tvShows.add(mapToTvShow(tmdbTvShow))
                trendingTvShows.add(TrendingTvShow(tmdbTvShow.id, index, timeWindow.toString()))
            }
            db.withTransaction {
                tvShowDao.insert(tvShows)
                tvShowDao.insertTrending(trendingTvShows)
            }
            return tvShows
        }
        return cachedTvShows

    }
}