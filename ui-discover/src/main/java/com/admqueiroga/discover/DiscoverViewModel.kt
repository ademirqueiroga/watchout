package com.admqueiroga.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.repository.MovieRepositoryImpl
import com.admqueiroga.data.repository.TvShowRepositoryImpl
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.TmdbApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiscoverViewModel(
    db: MovieDb,
    movieApi: TmdbApiService.V3.Movies,
    tvShowApi: TmdbApiService.V3.TvShows,
) : ViewModel() {

    private val tmdb = TmdbApiClient()
    private val trendingApi = tmdb.trending

    private val _state: MutableStateFlow<DiscoverState> = MutableStateFlow(DiscoverState())
    val state: StateFlow<DiscoverState> =
        _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    private val movieRepo = MovieRepositoryImpl(db, movieApi)
    private val tvShowRepo = TvShowRepositoryImpl(db, tvShowApi)

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(movies = movieRepo.trending(TmdbApiService.V3.TimeWindow.Day))
            }
            _state.update {
                it.copy(tvShows = tvShowRepo.trending(TmdbApiService.V3.TimeWindow.Day))
            }
//            _state.update {
//                it.copy(people = (movieApi.people() as? NetworkResponse.Success)?.body?.results ?: emptyList())
//            }
            _state.update {
                it.copy(requestToken = (tmdb.auth.newToken() as? NetworkResponse.Success)?.body?.requestToken)
            }
        }
        loadFreeToWatch(0)
    }

    fun loadTrendingMovies(timeWindow: TmdbApiService.V3.TimeWindow) = viewModelScope.launch {
        _state.update {
            it.copy(movies = movieRepo.trending(timeWindow))
        }
    }

    fun loadTrendingTvShows(timeWindow: TmdbApiService.V3.TimeWindow) = viewModelScope.launch {
        _state.update {
            it.copy(tvShows = tvShowRepo.trending(timeWindow))
        }
    }

    fun loadFreeToWatch(filter: Int) = viewModelScope.launch {
        val repository = FreeToWatchRepository()
        _state.update {
            withContext(Dispatchers.IO) {
                when (filter) {
                    0 -> it.copy(freeToWatch = repository.getFreeToWatchMovies())
                    else -> it.copy(freeToWatch = repository.getFreeToWatchTvShows())
                }
            }
        }
    }

    class Factory(
        private val db: MovieDb,
        private val movieApi: TmdbApiService.V3.Movies,
        private val tvShowApi: TmdbApiService.V3.TvShows
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DiscoverViewModel(db, movieApi, tvShowApi) as T
        }
    }


}