package com.admqueiroga.data.repository

import kotlinx.coroutines.flow.Flow
import com.admqueiroga.data.model.MovieGenre

interface MovieGenreRepository {

    suspend fun insert(genre: List<MovieGenre>)

    suspend fun insert(genre: MovieGenre)

    fun flow(): Flow<List<MovieGenre>>

}