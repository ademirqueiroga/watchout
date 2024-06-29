package com.admqueiroga.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.TmdbApiService
import com.admqueiroga.data.tmdb.model.TmdbTvShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TvShowViewModel(tmdbApi: TmdbApiService.V3.TvShows) : ViewModel() {

    private val _popular = MutableStateFlow<List<TmdbTvShow>>(emptyList())
    val popular: StateFlow<List<TmdbTvShow>> = _popular
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            when (val result = tmdbApi.trending(TmdbApiService.V3.TimeWindow.Day)) {
                is NetworkResponse.Success -> _popular.value = result.body.results
                else -> {}
            }
        }
    }

    class Factory(val tmdbApi: TmdbApiService.V3.TvShows) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TvShowViewModel(tmdbApi) as T
        }
    }

}