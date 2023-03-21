package com.admqueiroga.data.local.dao

import androidx.room.*
import com.admqueiroga.data.model.Movie

@Dao
interface MovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie.Details>)

    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun get(movieId: Long): Movie.Details?

    @Delete
    suspend fun delete(movie: Movie)

}