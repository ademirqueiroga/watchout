package com.admqueiroga.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.admqueiroga.data.tmdb.repository.TmdbMovieRepository
import com.admqueiroga.data.tmdb.model.TmdbMovieGenre
import com.admqueiroga.data.tmdb.model.TmdbMovie

class MoviePagingSource(
    val tmdbMovieRepository: TmdbMovieRepository,
    val genres: List<TmdbMovieGenre>? = null,
) : PagingSource<Int, TmdbMovie>() {

    override fun getRefreshKey(state: PagingState<Int, TmdbMovie>): Int {
//            return (state.anchorPosition ?: 0) - state.config.initialLoadSize / 2).coerceAtLeast(0)
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbMovie> {
        val page = params.key ?: 1
        return try {
            val moviePage = tmdbMovieRepository.movies(page, genres)
            TODO()
//            val itemsAfter = moviePage.totalResults - ((moviePage.totalPages - page) * 20)
//            LoadResult.Page(
//                data = moviePage.results,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (itemsAfter > 0) page + 1 else null,
//                itemsAfter = itemsAfter,
//            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
