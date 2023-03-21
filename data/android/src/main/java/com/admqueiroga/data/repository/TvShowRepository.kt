package com.admqueiroga.data.repository

import com.admqueiroga.data.model.GenreWithTvShows
import com.admqueiroga.data.model.TvShow

interface TvShowRepository {

    suspend fun insert(tvShow: TvShow)
    suspend fun insert(tvShows: List<TvShow>)
    suspend fun insert(details: TvShow.Details)
    suspend fun details(tvShowId: Long): TvShow.Details?
    suspend fun getByGenre(genreId: Long): GenreWithTvShows?

}