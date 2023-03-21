package com.admqueiroga.tvshow

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.admqueiroga.data.tmdb.repository.TmdbTvShowRepository
import com.admqueiroga.data.tmdb.model.TmdbMovieGenre
import com.admqueiroga.data.tmdb.model.TmdbTvShow

class TvShowPagingSource(
    val tmdbTvShowRepository: TmdbTvShowRepository,
    val genres: List<TmdbMovieGenre>? = null,
) : PagingSource<Int, TmdbTvShow>() {

    override fun getRefreshKey(state: PagingState<Int, TmdbTvShow>): Int {
//            return (state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbTvShow> {
        val page = params.key ?: 1
        return try {
            val moviePage = tmdbTvShowRepository.shows(page, genres)
            TODO()
//            LoadResult.Page(
//                data = moviePage.results,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = page + 1,
//                itemsAfter = moviePage.totalResults - ((moviePage.totalPages - page) * 20),
//            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
