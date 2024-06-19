package com.admqueiroga.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.admqueiroga.data.NetworkResponse
import com.admqueiroga.data.tmdb.TmdbApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SearchViewModel : ViewModel() {
    val tmdbApi = TmdbApiClient()
    val searchText = MutableStateFlow("")
    val searchResults = searchText.debounce(300).flatMapLatest { searchTerm ->
        // Perform search and return results
        flow {
            emit(
                (tmdbApi.search.movies(searchTerm) as NetworkResponse.Success).body.results
            )
        }
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val isLoading = searchResults.map { it.isEmpty() }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )

    fun onSearchTextChanged(newText: String) {
        searchText.value = newText
    }
}