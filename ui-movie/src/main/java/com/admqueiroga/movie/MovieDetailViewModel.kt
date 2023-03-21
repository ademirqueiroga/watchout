package com.admqueiroga.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.model.TmdbMovieDetail
import com.admqueiroga.data.tmdb.repository.TmdbMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val movieId: Long) : ViewModel() {

    private val repo = TmdbMovieRepository(TmdbApiClient().movies)

    private val _details = MutableStateFlow<TmdbMovieDetail?>(null)
    val details: Flow<TmdbMovieDetail?> = _details

    fun loadDetails() = viewModelScope.launch(Dispatchers.IO) {
        _details.value = (repo.detail(movieId) as? NetworkResponse.Success)?.body
    }

    class Factory(private val movieId: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(movieId) as T
        }
    }

}