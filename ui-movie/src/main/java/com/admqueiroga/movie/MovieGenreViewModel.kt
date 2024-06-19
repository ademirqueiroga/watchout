package com.admqueiroga.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.remote.MoviesRemoteMediator
import com.admqueiroga.data.repository.MovieGenreRepositoryImpl
import com.admqueiroga.data.tmdb.TmdbApiService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class MovieGenreViewModel(
    genreId: Long,
    db: MovieDb,
    moviesApi: TmdbApiService.V3.Movies,
) : ViewModel() {

    private val genresRepo = MovieGenreRepositoryImpl(db, moviesApi)

    val genre = flow {
        emit(genresRepo.get(genreId))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MovieGenre(genreId, ""))

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(20),
        remoteMediator = MoviesRemoteMediator(genreId, db, moviesApi),
        pagingSourceFactory = {
            db.movieDao().pagingSource(genreId)
        }
    ).flow.cachedIn(viewModelScope)

    class Factory(
        private val genreId: Long,
        private val db: MovieDb,
        private val moviesApi: TmdbApiService.V3.Movies,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieGenreViewModel(genreId, db, moviesApi) as T
        }
    }

}