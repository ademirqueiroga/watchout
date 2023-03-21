package com.admqueiroga.people

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.admqueiroga.data.tmdb.repository.TmdbPeopleRepository
import com.admqueiroga.data.tmdb.model.TmdbPerson

class PeoplePagingDataSource(val tmdbPeopleRepository: TmdbPeopleRepository): PagingSource<Int, TmdbPerson>() {

    val pager = Pager(
        initialKey = 1,
        config = PagingConfig(20, enablePlaceholders = false),
        pagingSourceFactory = {
            PeoplePagingDataSource(tmdbPeopleRepository)
        }
    )

    override fun getRefreshKey(state: PagingState<Int, TmdbPerson>): Int {
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TmdbPerson> {
        val page = params.key ?: 1
        return try {
            val peoplePage = tmdbPeopleRepository.popular(page)
            TODO()
//            val itemsAfter = peoplePage.totalResults - ((peoplePage.totalPages - page) * 20)
//            LoadResult.Page(
//                data = peoplePage.results,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (itemsAfter > 0) page + 1 else null,
//                itemsAfter = itemsAfter
//            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}