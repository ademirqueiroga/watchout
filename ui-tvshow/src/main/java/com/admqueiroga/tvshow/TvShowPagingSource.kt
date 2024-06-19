package com.admqueiroga.tvshow

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.tmdb.repository.TmdbTvShowRepository
import com.admqueiroga.data.tmdb.model.TmdbMovieGenre
import com.admqueiroga.data.tmdb.model.TmdbTvShow

class TvShowPagingSource(
    private val tmdbTvShowRepository: TmdbTvShowRepository,
    private val genres: LongArray = LongArray(0),
) : PagingSource<Int, TmdbTvShow>() {

    override fun getRefreshKey(state: PagingState<Int, TmdbTvShow>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbTvShow> {
        val page = params.key ?: 1
        return when (val apiResponse = tmdbTvShowRepository.shows(page, *genres)) {
            is NetworkResponse.Success -> {
                val moviePage = apiResponse.body
                val itemsAfter = moviePage.totalResults - ((moviePage.totalPages - page) * 20)
                LoadResult.Page(
                    data = moviePage.results,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (itemsAfter > 0) page + 1 else null,
                    itemsAfter = itemsAfter,
                )
            }

            is NetworkResponse.ApiError -> LoadResult.Error(Exception(apiResponse.body.message))
            is NetworkResponse.NetworkError -> LoadResult.Error(apiResponse.error)
            is NetworkResponse.UnknownError -> LoadResult.Error(apiResponse.error)
        }

    }

}
