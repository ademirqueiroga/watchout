package com.admqueiroga.data.repository

import com.admqueiroga.data.model.TvShowGenre
import kotlinx.coroutines.flow.Flow

interface TvShowGenreRepository {

    suspend fun insert(genre: List<TvShowGenre>)

    suspend fun insert(genre: TvShowGenre)

    fun flow(): Flow<List<TvShowGenre>>

}