package com.admqueiroga.movie

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.admqueiroga.data.model.GenreWithMovies
import com.admqueiroga.data.local.MovieDb
import com.admqueiroga.data.repository.MovieGenreRepositoryImpl
import com.admqueiroga.data.repository.MovieRepositoryImpl
import com.admqueiroga.data.tmdb.TmdbApiClient
import com.admqueiroga.data.tmdb.model.TmdbMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviesViewModel(
    db: MovieDb
) : ViewModel() {

    private val genresRepo = MovieGenreRepositoryImpl(
        db,
        TmdbApiClient().movies,
    )


    private val _moviesByGenre = MutableStateFlow<ArrayList<GenreWithMovies>>(ArrayList())
    val moviesByGenre = mutableStateListOf<GenreWithMovies>()

    private val _featuredMovies = MutableStateFlow<ArrayList<TmdbMovie>>(ArrayList())
    val featuredMovies = mutableStateListOf<TmdbMovie>()

    private val movieRepo = MovieRepositoryImpl(db, TmdbApiClient().movies)

    init {
        viewModelScope.launch {
            genresRepo.flow().collectLatest { genres ->
                moviesByGenre.clear()
                for (genre in genres) {
                    val genreWithMovies = movieRepo.getByGenre(genre.id) ?: continue
                    if (genreWithMovies.movies.isNotEmpty()) {
                        moviesByGenre.add(genreWithMovies)
                    }
                }
            }
        }

//        viewModelScope.launch {
//            featuredMovies.addAll(movieRepo.popular().results)
//        }
    }

    class Factory(val db: MovieDb) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(db) as T
        }
    }

}