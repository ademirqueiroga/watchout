package com.admqueiroga.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.admqueiroga.data.model.GenreWithMovies
import com.admqueiroga.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun pagingSource(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun logMovieRefresh(genreMovieRemoteKey: com.admqueiroga.data.GenreMovieRemoteKey)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM movie_genres WHERE genre_id = :genreId")
    suspend fun movies(genreId: Long): GenreWithMovies?

}