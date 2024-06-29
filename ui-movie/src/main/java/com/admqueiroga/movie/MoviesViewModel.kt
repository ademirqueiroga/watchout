package com.admqueiroga.movie

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.repository.MovieGenreRepositoryImpl
import com.admqueiroga.data.repository.MovieRepositoryImpl
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.model.TmdbNewSessionRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MoviesViewModel(
    db: MovieDb,
    val tmdb: TmdbApiClient
) : ViewModel() {

    private val movieRepo = MovieRepositoryImpl(db, tmdb.movies)
    private val genresRepo = MovieGenreRepositoryImpl(db, tmdb.movies)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    val moviesByGenre = genresRepo.flow()
        .map { genres ->
            genres.mapNotNull {
                movieRepo.getByGenre(it.id)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            moviesByGenre.value.forEach {
                genresRepo.deleteCrossRefs(it.genre.id)
            }
            genresRepo.refresh()
            _isRefreshing.emit(false)
        }
    }

    suspend fun authenticate(): String? {
        return when (val token = tmdb.auth.newToken()) {
            is NetworkResponse.Success -> token.body.requestToken
            else -> null
        }
    }

    class Factory(val db: MovieDb, val tmdb: TmdbApiClient) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(db, tmdb) as T
        }
    }

}