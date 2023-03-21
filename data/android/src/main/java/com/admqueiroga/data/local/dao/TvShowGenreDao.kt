package com.admqueiroga.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.admqueiroga.data.model.TvShowGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowGenreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genre: TvShowGenre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genres: List<TvShowGenre>)

    @Query("SELECT * FROM tv_show_genres")
    fun flow(): Flow<List<TvShowGenre>>

}