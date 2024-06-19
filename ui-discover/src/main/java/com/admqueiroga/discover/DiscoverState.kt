package com.admqueiroga.discover

import com.admqueiroga.data.model.Movie
import com.admqueiroga.data.model.MovieGenre
import com.admqueiroga.data.model.TvShow
import com.admqueiroga.data.model.TvShowGenre
import com.admqueiroga.data.tmdb.TmdbApiService
import com.admqueiroga.data.tmdb.model.TmdbContent
import com.admqueiroga.data.tmdb.model.TmdbMovie
import com.admqueiroga.data.tmdb.model.TmdbPerson
import com.admqueiroga.data.tmdb.model.TmdbTvShow

data class DiscoverState(
    val movies: List<Movie> = emptyList(),
    val tvShows: List<TvShow> = emptyList(),
    val people: List<TmdbPerson> = emptyList(),
    val freeToWatch: List<TmdbContent> = emptyList(),
    val requestToken: String? = null,
)