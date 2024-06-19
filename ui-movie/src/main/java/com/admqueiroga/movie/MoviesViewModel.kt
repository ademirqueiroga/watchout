package com.admqueiroga.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.repository.MovieGenreRepositoryImpl
import com.admqueiroga.data.repository.MovieRepositoryImpl
import com.admqueiroga.data.tmdb.TmdbApiClient
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MoviesViewModel(
    db: MovieDb,
    tmdb: TmdbApiClient
) : ViewModel() {

    private val movieRepo = MovieRepositoryImpl(db, tmdb.movies)
    private val genresRepo = MovieGenreRepositoryImpl(db, tmdb.movies)

    val moviesByGenre = genresRepo.flow()
        .map { genres ->
            genres.mapNotNull {
                movieRepo.getByGenre(it.id)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    class Factory(val db: MovieDb, val tmdb: TmdbApiClient) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(db, tmdb) as T
        }
    }

}